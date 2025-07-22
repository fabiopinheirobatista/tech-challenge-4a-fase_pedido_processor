package br.com.fiap.mspedidoprocessor.core.gateways;

import java.math.BigDecimal;

public interface ProdutoServiceGateway {
    boolean skuExiste(String sku);
    BigDecimal obterPreco(String sku);
}