package br.com.fiap.mspedidoprocessor.adapter.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PagamentoCallback {
    private Long pedidoId;
    private String status;
}
