package io.github.zealbuquerque.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column
    private Integer quantidade;


}
