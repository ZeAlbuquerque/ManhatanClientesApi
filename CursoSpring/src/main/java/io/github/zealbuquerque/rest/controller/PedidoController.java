package io.github.zealbuquerque.rest.controller;

import io.github.zealbuquerque.domain.entity.ItemPedido;
import io.github.zealbuquerque.domain.entity.Pedido;
import io.github.zealbuquerque.domain.enums.StatusPedido;
import io.github.zealbuquerque.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.zealbuquerque.rest.dto.InformacoesItemPedidoDTO;
import io.github.zealbuquerque.rest.dto.InformacoesPedidoDTO;
import io.github.zealbuquerque.rest.dto.PedidoDTO;
import io.github.zealbuquerque.service.PedidoService;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save (@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }


    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map( p -> converter(p) )
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
       return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItems()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converter (List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

        return items.stream().map(
                item -> InformacoesItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPrecoUnitario())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,
                             @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }





}
