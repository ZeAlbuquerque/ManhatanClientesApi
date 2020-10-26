package io.github.zealbuquerque.service;

import io.github.zealbuquerque.domain.entity.Pedido;
import io.github.zealbuquerque.domain.enums.StatusPedido;
import io.github.zealbuquerque.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
