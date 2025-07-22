package br.com.fiap.mspedidoprocessor.adapter.gateway;

import br.com.fiap.mspedidoprocessor.adapter.external.dto.Pedido;
import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import br.com.fiap.mspedidoprocessor.core.usecase.pedidoprocessor.PedidoProcessorUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class KafkaPedidoConsumer {

    private final PedidoProcessorUseCase pedidoProcessor;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "topico-pedido-reciver", groupId = "grupo-pedido-reciver")
    public void ouvir(ConsumerRecord<String, String> record) {
        try {
            String json = record.value();
            Pedido pedidoRetorno = objectMapper.readValue(json, Pedido.class);

            PedidoProcessor pedidoProcessor = new PedidoProcessor();
            pedidoProcessor.setPedidoReciverId(pedidoRetorno.getId());
            //pedidoProcessor.setStatus(pedidoRetorno.getStatus());
            pedidoProcessor.setStatus(br.com.fiap.mspedidoprocessor.core.domain.PedidoStatus.valueOf(pedidoRetorno.getStatus().name()));
            pedidoProcessor.setClienteId(pedidoRetorno.getClienteId());

            //pedidoProcessor.setItens(pedidoRetorno.getItens());
            pedidoProcessor.setItens(pedidoRetorno.getItens().stream()
                    .map(item -> new br.com.fiap.mspedidoprocessor.core.domain.ItemPedidoProcessor(
                            item.getSku(),
                            item.getQuantidade(),
                            new BigDecimal(0L),
                            new BigDecimal(0L)
                    ))
                    .collect(java.util.stream.Collectors.toList()));


            this.pedidoProcessor.processar(pedidoProcessor, "123");
        } catch (Exception e) {
            e.printStackTrace(); // idealmente, log estruturado
        }
    }
}