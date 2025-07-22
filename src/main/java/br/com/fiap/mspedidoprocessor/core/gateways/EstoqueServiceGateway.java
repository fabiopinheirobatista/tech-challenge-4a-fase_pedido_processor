package br.com.fiap.mspedidoprocessor.core.gateways;

public interface EstoqueServiceGateway {
    boolean debitarEstoque(String sku, int quantidade);
    void reverterEstoque(String sku, int quantidade);
}