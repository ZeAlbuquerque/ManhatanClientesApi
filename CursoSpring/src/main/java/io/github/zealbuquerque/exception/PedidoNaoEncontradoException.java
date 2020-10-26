package io.github.zealbuquerque.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado");
    }
}
