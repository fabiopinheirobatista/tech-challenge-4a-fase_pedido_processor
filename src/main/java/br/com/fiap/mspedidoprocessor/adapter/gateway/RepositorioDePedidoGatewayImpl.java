package br.com.fiap.mspedidoprocessor.adapter.gateway;

import br.com.fiap.mspedidoprocessor.adapter.persistence.entity.PedidoProcessorEntity;
import br.com.fiap.mspedidoprocessor.adapter.persistence.repository.PedidoRepositoryJpa;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.gateways.PedidoGateway;
import br.com.fiap.mspedidoprocessor.adapter.mapper.PedidoProcessorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RepositorioDePedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepositoryJpa pedidoRepositoryJpa;
    private final PedidoProcessorMapper pedidoProcessorMapper;

    @Override
    public PedidoProcessor salvar(PedidoProcessor pedidoProcessor) {
        if (pedidoProcessor.getId() == null) {
            pedidoProcessor.setId(UUID.randomUUID());
        }
        PedidoProcessorEntity pedidoEntity = pedidoProcessorMapper.toEntity(pedidoProcessor);

        PedidoProcessorEntity save = pedidoRepositoryJpa.save(pedidoEntity);
        return pedidoProcessorMapper.toModel(save);
    }

//    @Override
//    public void atualizar(PedidoProcessor pedidoProcessor) {
//        salvar(pedidoProcessor);
//    }
}
