package br.com.fiap.mspedidoprocessor.adapter.external.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Pedido {
    private Long id;
    private Long clienteId;
    private String numeroCartao;
    private List<ItemPedido> itens;
    private PedidoStatus status;
    private LocalDateTime dataCriacao;
}
