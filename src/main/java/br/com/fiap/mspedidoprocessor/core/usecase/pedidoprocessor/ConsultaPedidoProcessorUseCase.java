package br.com.fiap.mspedidoprocessor.core.usecase.pedidoprocessor;

import br.com.fiap.mspedidoprocessor.core.domain.PagamentoCallback;
import br.com.fiap.mspedidoprocessor.core.gateways.ConsultaPedidoProcessorGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultaPedidoProcessorUseCase {

    private final ConsultaPedidoProcessorGateway consultaPedidoProcessorGateway;

    public PagamentoCallback consultarPagamentoCallback(Long pedidoId) {
        return consultaPedidoProcessorGateway.consultarPagamentoCallback(pedidoId);
    }
}
