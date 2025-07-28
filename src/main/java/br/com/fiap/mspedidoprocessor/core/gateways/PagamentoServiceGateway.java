package br.com.fiap.mspedidoprocessor.core.gateways;

import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoResponseDTO;
import org.springframework.http.ResponseEntity;

public interface PagamentoServiceGateway {
    ResponseEntity<PagamentoResponseDTO> solicitarPagamento(PagamentoRequestDTO request);
}