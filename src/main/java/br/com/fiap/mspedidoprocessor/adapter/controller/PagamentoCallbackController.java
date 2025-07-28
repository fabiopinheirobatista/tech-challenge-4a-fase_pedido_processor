package br.com.fiap.mspedidoprocessor.adapter.controller;


import br.com.fiap.mspedidoprocessor.adapter.controller.response.PagamentoCallbackResponseDTO;
import br.com.fiap.mspedidoprocessor.adapter.external.clientkafka.dto.Pedido;
import br.com.fiap.mspedidoprocessor.adapter.external.clientkafka.dto.PedidoStatus;
import br.com.fiap.mspedidoprocessor.adapter.mapper.PedidoProcessorMapper;
import br.com.fiap.mspedidoprocessor.core.domain.PagamentoCallback;
import br.com.fiap.mspedidoprocessor.core.gateways.ConsultaPedidoProcessorGateway;
import br.com.fiap.mspedidoprocessor.core.gateways.EstoqueServiceGateway;
import br.com.fiap.mspedidoprocessor.core.gateways.PedidoGateway;
import br.com.fiap.mspedidoprocessor.core.usecase.pedidoprocessor.ConsultaPedidoProcessorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/pagamento")
public class PagamentoCallbackController {

    private final PedidoGateway pedidoGateway;
    private final EstoqueServiceGateway estoqueService;
    private final ConsultaPedidoProcessorUseCase consultaPedidoProcessorUseCase;

    private final PedidoProcessorMapper pedidoProcessorMapper;

    @GetMapping("/consultar/{id}")
    public ResponseEntity<PagamentoCallbackResponseDTO> consultaPagamento(@PathVariable Long id) {

        PagamentoCallback pagamentoCallback = consultaPedidoProcessorUseCase.consultarPagamentoCallback(id);
        PagamentoCallbackResponseDTO pagamentoCallbackResponseDTO = pedidoProcessorMapper.toPagamentoCallbackResponseDTO(pagamentoCallback);
        return ResponseEntity.ok(pagamentoCallbackResponseDTO);

    }

}