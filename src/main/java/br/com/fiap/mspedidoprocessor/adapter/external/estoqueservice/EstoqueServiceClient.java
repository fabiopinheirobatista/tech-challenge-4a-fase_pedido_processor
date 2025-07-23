package br.com.fiap.mspedidoprocessor.adapter.external.estoqueservice;

import br.com.fiap.mspedidoprocessor.core.gateways.EstoqueServiceGateway;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "estoque-service", url = "${services.estoque}")
public interface EstoqueServiceClient extends EstoqueServiceGateway {

    @PostMapping("/estoque/debitar")
    boolean debitarEstoque(@RequestParam("sku") String sku,
                        @RequestParam("quantidade") int quantidade);

    @PostMapping("/estoque/repor")
    void reverterEstoque(@RequestParam("sku") String sku,
                        @RequestParam("quantidade") int quantidade);


}