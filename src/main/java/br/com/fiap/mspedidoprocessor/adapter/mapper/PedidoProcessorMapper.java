package br.com.fiap.mspedidoprocessor.adapter.mapper;

import br.com.fiap.mspedidoprocessor.adapter.persistence.entity.ItemPedidoProcessorEntity;
import br.com.fiap.mspedidoprocessor.adapter.persistence.entity.PedidoProcessorEntity;
import br.com.fiap.mspedidoprocessor.adapter.external.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.fiap.mspedidoprocessor.core.domain.ItemPedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PedidoProcessorMapper {

    public PedidoProcessor toModel(PedidoProcessorEntity entity) {
        if (entity == null) {
            return null;
        }

        return PedidoProcessor.builder()
                .id(entity.getId())
                .pedidoReciverId(entity.getPedidoReciverId())
                .clienteId(entity.getClienteId())
                .total(entity.getTotal())
                .status(PedidoStatus.valueOf(entity.getStatus()))
                .itens(entity.getItens().stream()
                        .map(this::toItemPedidoProcessor)
                        .toList())
                .build();
    }

    private ItemPedidoProcessor toItemPedidoProcessor(ItemPedidoProcessorEntity entity) {
        if (entity == null) {
            return null;
        }
        return ItemPedidoProcessor.builder()
                .sku(entity.getSku())
                .quantidade(entity.getQuantidade())
                .precoUnitario(entity.getPrecoUnitario())
                .precoTotal(entity.getPrecoTotal())
                .build();
    }

    public PedidoProcessorEntity toEntity(PedidoProcessor model) {
        if (model == null) {
            return null;
        }


        return PedidoProcessorEntity.builder()
                .id(model.getId())
                .pedidoReciverId(model.getPedidoReciverId())
                .clienteId(model.getClienteId())
                .total(model.getTotal())
                .status(model.getStatus().name())
                .criadoEm(LocalDateTime.now())
                .itens(model.getItens().stream()
                        .map(this::toItemPedidoProcessorEntity)
                        .toList())
                .build();
    }

    private ItemPedidoProcessorEntity toItemPedidoProcessorEntity(ItemPedidoProcessor item) {
        if (item == null) {
            return null;
        }
        return ItemPedidoProcessorEntity.builder()
                .sku(item.getSku())
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .precoTotal(item.getPrecoTotal())
                .build();
    }

    public PagamentoRequestDTO toPagamentoRequestDTO(PedidoProcessor pedidoProcessor, String numeroCartao) {
        if (pedidoProcessor == null) {
            return null;
        }

        return new PagamentoRequestDTO(
                longToUUID(pedidoProcessor.getId()),
                pedidoProcessor.getTotal(),
                numeroCartao
        );
    }

    private UUID longToUUID(String value) {
        return value != null ? UUID.fromString(value.toString()) : null;
    }
}