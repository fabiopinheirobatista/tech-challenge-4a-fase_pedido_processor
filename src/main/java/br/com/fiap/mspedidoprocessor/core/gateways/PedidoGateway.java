package br.com.fiap.mspedidoprocessor.core.gateways;

import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;

public interface PedidoGateway {
    PedidoProcessor salvar(PedidoProcessor pedidoProcessor);
}