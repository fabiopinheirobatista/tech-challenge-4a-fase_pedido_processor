package br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice;

import br.com.fiap.mspedidoprocessor.adapter.external.clientkafka.dto.PagamentoDTO;
import br.com.fiap.mspedidoprocessor.core.gateways.PagamentoServiceGateway;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pagamento-service", url = "${services.pagamento}")
public interface PagamentoServiceClient extends PagamentoServiceGateway {

    @PostMapping("/pagamentos")
    void solicitarPagamento(@RequestBody PagamentoDTO dto);
}