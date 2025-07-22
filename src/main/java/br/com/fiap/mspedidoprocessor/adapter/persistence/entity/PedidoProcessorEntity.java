package br.com.fiap.mspedidoprocessor.adapter.persistence.entity;

import br.com.fiap.mspedidoprocessor.core.domain.PedidoProcessor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido_processor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoProcessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_reciver_id")
    private Long pedidoReciverId;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "total")
    private BigDecimal total;

    private String status;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "pedidoProcessor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedidoProcessorEntity> itens;

    public PedidoProcessorEntity(PedidoProcessor pedidoProcessor) {
        this.id = pedidoProcessor.getId();
        this.pedidoReciverId = pedidoProcessor.getPedidoReciverId();
        this.clienteId = pedidoProcessor.getClienteId();
        this.total = pedidoProcessor.getTotal();
        this.status = pedidoProcessor.getStatus().name();
//        if (pedidoProcessor.getItens() != null) {
//            this.itens = pedidoProcessor.getItens().stream()
//                    .map(item -> ItemPedidoProcessorEntity.builder()
//                            .itemPedidoProcessor(item)
//                            .pedidoProcessor(this)
//                            .build())
//                    .toList();
//        }
    }
}