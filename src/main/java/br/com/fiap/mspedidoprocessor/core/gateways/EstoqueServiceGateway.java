package br.com.fiap.mspedidoprocessor.core.gateways;

import br.com.fiap.mspedidoprocessor.adapter.external.estoqueservice.dto.BaixaEstoqueRequestDTO;
import org.springframework.http.ResponseEntity;

public interface EstoqueServiceGateway {
    ResponseEntity<Void> debitarEstoque(BaixaEstoqueRequestDTO request);
    void reverterEstoque(String sku, int quantidade);
}