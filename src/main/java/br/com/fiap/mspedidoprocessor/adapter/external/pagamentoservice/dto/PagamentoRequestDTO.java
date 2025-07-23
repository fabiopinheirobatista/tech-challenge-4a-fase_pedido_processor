package br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoRequestDTO(
        UUID pedidoId,
        BigDecimal valor,
        String numeroCartao) {
}
