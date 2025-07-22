package br.com.fiap.mspedidoprocessor.adapter.external.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedido {
    private Long id;
    private String sku;
    private Integer quantidade;
}
