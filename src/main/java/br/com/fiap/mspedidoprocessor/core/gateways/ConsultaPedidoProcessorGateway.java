package br.com.fiap.mspedidoprocessor.core.gateways;

import br.com.fiap.mspedidoprocessor.core.domain.PagamentoCallback;

public interface ConsultaPedidoProcessorGateway {

    PagamentoCallback consultarPagamentoCallback(Long pedidoId);

}
