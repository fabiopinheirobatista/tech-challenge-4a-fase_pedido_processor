package br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice;

import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoResponseDTO;
import br.com.fiap.mspedidoprocessor.core.gateways.PagamentoServiceGateway;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pagamento-service", url = "${services.pagamento}")
public interface PagamentoServiceClient extends PagamentoServiceGateway {

    @PostMapping("/pagamentos")
    PagamentoResponseDTO solicitarPagamento(@RequestBody PagamentoRequestDTO request);

//    @GetMapping("/pagamentos/{idPagamento}")
//    ResponseEntity<PagamentoResponseDTO> consultarStatus(@PathVariable("idPagamento") UUID idPagamento);
}