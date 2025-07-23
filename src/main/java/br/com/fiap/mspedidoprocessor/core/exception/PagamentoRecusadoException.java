package br.com.fiap.mspedidoprocessor.core.exception;

public class PagamentoRecusadoException extends PedidoProcessorException {
    public PagamentoRecusadoException(String motivo) {
        super("Pagamento recusado: " + motivo);
    }
}