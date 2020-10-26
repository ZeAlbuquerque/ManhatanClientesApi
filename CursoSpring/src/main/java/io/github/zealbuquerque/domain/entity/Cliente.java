package io.github.zealbuquerque.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name  = "nome", length = 100)
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
