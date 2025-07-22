package br.com.fiap.mspedidoprocessor.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class ItemPedidoProcessor {
    private String sku;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
}
