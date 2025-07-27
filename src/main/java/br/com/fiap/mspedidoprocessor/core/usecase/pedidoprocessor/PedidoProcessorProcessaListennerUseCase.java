package br.com.fiap.mspedidoprocessor.core.usecase.pedidoprocessor;

import br.com.fiap.mspedidoprocessor.adapter.external.estoqueservice.dto.BaixaEstoqueRequestDTO;
import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoResponseDTO;
import br.com.fiap.mspedidoprocessor.adapter.external.produtoservice.dto.ProdutoDtoResponse;
import br.com.fiap.mspedidoprocessor.adapter.mapper.PedidoProcessorMapper;
import br.com.fiap.mspedidoprocessor.core.domain.ItemPedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoStatus;
import br.com.fiap.mspedidoprocessor.core.exception.*;
import br.com.fiap.mspedidoprocessor.core.gateways.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class PedidoProcessorProcessaListennerUseCase {

    private final PedidoGateway pedidoGateway;
    private final ClienteServiceGateway clienteService;
    private final ProdutoServiceGateway produtoService;
    private final EstoqueServiceGateway estoqueService;
    private final PagamentoServiceGateway pagamentoService;
    private final PedidoProcessorMapper pedidoProcessorMapper;


    public void processar(PedidoProcessor pedidoProcessor) {
        pedidoProcessor.setStatus(PedidoStatus.ABERTO);
        pedidoGateway.salvar(pedidoProcessor);

        this.validarCliente(pedidoProcessor);

        this.calculaTotal(pedidoProcessor);

        this.baixarNoEstoque(pedidoProcessor);

        PagamentoRequestDTO request = pedidoProcessorMapper.toPagamentoRequestDTO(pedidoProcessor);
        PagamentoResponseDTO resposta = validamento(request, pedidoProcessor);
        var status = switch (resposta.status()) {
            case "APROVADO" -> PedidoStatus.PAGAMENTO_APROVADO;
            case "PROCESSANDO" -> PedidoStatus.PAGAMENTO_PROCESSANDO;
            case "RECUSADO" -> PedidoStatus.PAGAMENTO_RECUSADO;
            default -> throw new PagamentoException("Status de pagamento inválido: " + resposta.status());
        };

        pedidoProcessor.setStatus(status);
        pedidoGateway.salvar(pedidoProcessor);

    }

    private PagamentoResponseDTO validamento(PagamentoRequestDTO request, PedidoProcessor pedidoProcessor) {
            PagamentoResponseDTO resposta = pagamentoService.solicitarPagamento(request).getBody();
            if (resposta == null || !resposta.status().matches("APROVADO")) {
                pedidoProcessor.getItens().forEach(item -> {
                    try {
                        estoqueService.reverterEstoque(new BaixaEstoqueRequestDTO(item.getSku(), item.getQuantidade()));
                    } catch (Exception ex) {
                        pedidoProcessor.setStatus(PedidoStatus.FALHA_PAGAMENTO);
                        pedidoGateway.salvar(pedidoProcessor);
                        throw new EstoqueException("Erro ao reverter estoque para SKU: " + item.getSku() + " - "+ex.getMessage(), ex);
                    }
                });
                pedidoProcessor.setStatus(PedidoStatus.FALHA_PAGAMENTO);
                pedidoGateway.salvar(pedidoProcessor);
                throw new PagamentoException("Resposta de pagamento inválida. Status: " + (resposta != null ? resposta.status() : "null"));
            }
            pedidoProcessor.setStatus(PedidoStatus.PAGAMENTO_APROVADO);
            pedidoGateway.salvar(pedidoProcessor);
            return resposta;

    }

    private void calculaTotal(PedidoProcessor pedidoProcessor){
        try {
            BigDecimal total = BigDecimal.ZERO;
            List<ItemPedidoProcessor> itens = pedidoProcessor.getItens();
            for (ItemPedidoProcessor item : itens) {
                ProdutoDtoResponse produtoDtoResponse = validarProduto(pedidoProcessor, item.getSku());
                produtoService.buscarProduto(item.getSku());
                if (produtoDtoResponse == null) {
                    pedidoProcessor.setStatus(PedidoStatus.FALHA_PROCESSAR_ESTOQUE);
                    pedidoGateway.salvar(pedidoProcessor);
                    return;
                }
                BigDecimal preco = produtoDtoResponse.preco();
                item.setPrecoUnitario(preco);
                BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
                item.setPrecoTotal(preco.multiply(quantidade));
                total = total.add(item.getPrecoTotal());
            }

            pedidoProcessor.setTotal(total);
        } catch (Exception e) {
            throw new EstoqueException(e);
        }
    }

    private void baixarNoEstoque(PedidoProcessor pedidoProcessor) {
        List<ItemPedidoProcessor> itens = pedidoProcessor.getItens();
        for (ItemPedidoProcessor item : itens) {
            try {
                estoqueService.baixaEstoque((new BaixaEstoqueRequestDTO(item.getSku(), item.getQuantidade())));
            } catch (Exception e) {
                estoqueService.reverterEstoque(new BaixaEstoqueRequestDTO(item.getSku(), item.getQuantidade()));
                pedidoProcessor.setStatus(PedidoStatus.FECHADO_SEM_ESTOQUE);
                pedidoGateway.salvar(pedidoProcessor);
                throw new EstoqueInsuficienteException(item.getSku());
            }

        }
        pedidoGateway.salvar(pedidoProcessor);
    }

    private void validarCliente(PedidoProcessor pedidoProcessor) {
        var retorno = clienteService.clienteExiste(pedidoProcessor.getClienteId());
        if (retorno==false){
            pedidoProcessor.setStatus(PedidoStatus.FALHA_CLIENTE_NAOENCONTRADO);
            pedidoGateway.salvar(pedidoProcessor);
            throw new ClienteException("Cliente não encontrado com ID: " + pedidoProcessor.getClienteId());
        }

    }

    private ProdutoDtoResponse validarProduto(PedidoProcessor pedidoProcessor, String sku) {
        try {
            return  produtoService.buscarProduto(sku);
        } catch (Exception e) {
            pedidoProcessor.setStatus(PedidoStatus.FALHA_PRODUTO_NAOENCONTRADO);
            pedidoGateway.salvar(pedidoProcessor);
            throw new ProdutoNaoEncontradoException(sku);
        }
    }
}