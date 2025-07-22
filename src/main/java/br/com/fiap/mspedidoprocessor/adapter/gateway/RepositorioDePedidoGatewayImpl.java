package br.com.fiap.mspedidoprocessor.adapter.gateway;

import br.com.fiap.mspedidoprocessor.adapter.persistence.entity.PedidoProcessorEntity;
import br.com.fiap.mspedidoprocessor.adapter.persistence.repository.PedidoRepositoryJpa;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.gateways.PedidoGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RepositorioDePedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepositoryJpa pedidoRepositoryJpa;
    private final ObjectMapper objectMapper;

    @Override
    public PedidoProcessor salvar(PedidoProcessor pedidoProcessor) {
        PedidoProcessorEntity pedidoEntity = new PedidoProcessorEntity(pedidoProcessor);
        PedidoProcessorEntity save = pedidoRepositoryJpa.save(pedidoEntity);
        return objectMapper.convertValue(save, PedidoProcessor.class);
    }

    @Override
    public void atualizar(PedidoProcessor pedidoProcessor) {
        salvar(pedidoProcessor);
    }
}
