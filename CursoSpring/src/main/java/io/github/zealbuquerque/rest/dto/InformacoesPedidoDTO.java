package io.github.zealbuquerque.rest.dto;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {
    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private double total;
    private String dataPedido;
    private String status;
    private List<InformacoesItemPedidoDTO> items;
}
