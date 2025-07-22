package br.com.fiap.mspedidoprocessor.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class PedidoProcessor {
    private Long id;
    private Long pedidoReciverId;
    private Long clienteId;
    private List<ItemPedidoProcessor> itens;
    private BigDecimal total;
    private PedidoStatus status;
}
