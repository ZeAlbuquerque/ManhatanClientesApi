package io.github.zealbuquerque.domain.repository;

import io.github.zealbuquerque.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Integer> {
}
