package io.github.zealbuquerque.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesItemPedidoDTO {
    private String descricaoProduto;
    private double precoUnitario;
    private Integer quantidade;
}
