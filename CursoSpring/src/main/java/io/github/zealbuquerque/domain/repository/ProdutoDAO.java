package io.github.zealbuquerque.domain.repository;

import io.github.zealbuquerque.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Integer> {


}
