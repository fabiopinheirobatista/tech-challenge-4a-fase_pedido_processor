package br.com.fiap.mspedidoprocessor.adapter.controller.response;

import br.com.fiap.mspedidoprocessor.adapter.external.clientkafka.dto.ItemPedido;
import br.com.fiap.mspedidoprocessor.adapter.external.clientkafka.dto.PedidoStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PagamentoCallbackResponseDTO(
        Long id,
        Long clienteId,
        String numeroCartao,
        PedidoStatus status,
        LocalDateTime dataCriacao
) {
}

