package br.com.fiap.mspedidoprocessor.core.usecase.pedidoprocessor;

import br.com.fiap.mspedidoprocessor.adapter.external.produtoservice.dto.ProdutoDtoResponse;
import br.com.fiap.mspedidoprocessor.core.domain.ItemPedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoStatus;
import br.com.fiap.mspedidoprocessor.core.gateways.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class PedidoProcessorUseCase {

    private final PedidoGateway pedidoGateway;
    private final ClienteServiceGateway clienteService;
    private final ProdutoServiceGateway produtoService;
    private final EstoqueServiceGateway estoqueService;
   // private final PagamentoServiceGateway pagamentoService;


    public void processar(PedidoProcessor pedidoProcessor, String numeroCartao) {
        boolean clienteExiste = clienteService.clienteExiste(pedidoProcessor.getClienteId());
        if (!clienteExiste) {
            pedidoProcessor.setStatus(PedidoStatus.FALHADO);
            pedidoGateway.atualizar(pedidoProcessor);
            return;
        }

        // Valida SKUs e calcula total
        BigDecimal total = BigDecimal.ZERO;
        List<ItemPedidoProcessor> itens = pedidoProcessor.getItens();
        for (ItemPedidoProcessor item : itens) {
            ProdutoDtoResponse produtoDtoResponse = produtoService.buscarProduto(item.getSku());
            if (produtoDtoResponse == null) {
                pedidoProcessor.setStatus(PedidoStatus.FALHADO);
                pedidoGateway.atualizar(pedidoProcessor);
                return;
            }

            BigDecimal preco = produtoDtoResponse.preco();
            item.setPrecoUnitario(preco);
            total = total.add(item.getPrecoTotal());
        }

        pedidoProcessor.setTotal(total);

        // Verifica estoque
        for (ItemPedidoProcessor item : itens) {
            boolean sucesso = estoqueService.debitarEstoque(item.getSku(), item.getQuantidade());
            if (!sucesso) {
                pedidoProcessor.setStatus(PedidoStatus.FECHADO_SEM_ESTOQUE);
                pedidoGateway.atualizar(pedidoProcessor);
                return;
            }
        }

        // Atualiza pedido antes de pagamento
        pedidoProcessor.setStatus(PedidoStatus.ABERTO);
        pedidoGateway.atualizar(pedidoProcessor);

        // Solicita pagamento
        //pagamentoService.solicitarPagamento(pedidoProcessor, numeroCartao);
    }
}