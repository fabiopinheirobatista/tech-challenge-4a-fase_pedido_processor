package br.com.fiap.mspedidoprocessor.core.gateways;

import br.com.fiap.mspedidoprocessor.adapter.external.estoqueservice.dto.BaixaEstoqueRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface EstoqueServiceGateway {
    ResponseEntity<Void> baixaEstoque(BaixaEstoqueRequestDTO request);
    void reverterEstoque(BaixaEstoqueRequestDTO request);
}