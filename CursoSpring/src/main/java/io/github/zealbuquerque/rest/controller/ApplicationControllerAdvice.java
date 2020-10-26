package io.github.zealbuquerque.rest.controller;

import io.github.zealbuquerque.exception.PedidoNaoEncontradoException;
import io.github.zealbuquerque.exception.RegraNegocioException;
import io.github.zealbuquerque.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioExpection (RegraNegocioException ex) {
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundExeception(PedidoNaoEncontradoException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
