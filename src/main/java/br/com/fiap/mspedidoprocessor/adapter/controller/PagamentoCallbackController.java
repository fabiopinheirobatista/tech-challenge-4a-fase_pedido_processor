package br.com.fiap.mspedidoprocessor.adapter.controller;


import br.com.fiap.mspedidoprocessor.adapter.external.clientkafka.dto.Pedido;
import br.com.fiap.mspedidoprocessor.core.gateways.EstoqueServiceGateway;
import br.com.fiap.mspedidoprocessor.core.gateways.PedidoGateway;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/pagamento")
public class PagamentoCallbackController {

    private final PedidoGateway pedidoGateway;
    private final EstoqueServiceGateway estoqueService;


    @PostMapping("/retorno")
    public void retornoPagamento(@RequestBody PagamentoCallback callback) {
//        Pedido pedido = pedidoRepository.buscarPorId(callback.pedidoId);
//
//        if (callback.getStatus().equalsIgnoreCase("OK")) {
//            pedido.setStatus(Status.FECHADO_COM_SUCESSO);
//        } else {
//            pedido.setStatus(Status.FECHADO_SEM_CREDITO);
//            pedido.getItens().forEach(item ->
//                    estoqueService.reverterEstoque(item.getSku(), item.getQuantidade()));
//        }
//
//        pedidoRepository.atualizar(pedido);
    }

    public static class PagamentoCallback {
        private Long pedidoId;
        private String status;

        // getters/setters
    }
}