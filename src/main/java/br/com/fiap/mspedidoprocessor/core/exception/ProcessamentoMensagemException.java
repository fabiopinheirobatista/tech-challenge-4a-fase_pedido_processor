package br.com.fiap.mspedidoprocessor.core.exception;

public class ProcessamentoMensagemException extends PedidoProcessorException {
    public ProcessamentoMensagemException(String mensagemKafka, Throwable cause) {
        super("Erro ao processar mensagem: " + mensagemKafka, cause);
    }
}