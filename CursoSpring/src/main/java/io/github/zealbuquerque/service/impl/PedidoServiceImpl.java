package io.github.zealbuquerque.service.impl;

import io.github.zealbuquerque.domain.entity.Cliente;
import io.github.zealbuquerque.domain.entity.ItemPedido;
import io.github.zealbuquerque.domain.entity.Pedido;
import io.github.zealbuquerque.domain.entity.Produto;
import io.github.zealbuquerque.domain.enums.StatusPedido;
import io.github.zealbuquerque.domain.repository.ClienteDAO;
import io.github.zealbuquerque.domain.repository.ItemPedidoDAO;
import io.github.zealbuquerque.domain.repository.PedidoDAO;
import io.github.zealbuquerque.domain.repository.ProdutoDAO;
import io.github.zealbuquerque.exception.PedidoNaoEncontradoException;
import io.github.zealbuquerque.exception.RegraNegocioException;
import io.github.zealbuquerque.rest.dto.ItemPedidoDTO;
import io.github.zealbuquerque.rest.dto.PedidoDTO;
import io.github.zealbuquerque.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO repository;
    private final ClienteDAO clientesRepository;
    private final ProdutoDAO produtosRepository;
    private final ItemPedidoDAO itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente invalido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedidos = converteritems(pedido, dto.getItems());
        repository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedidos);
        pedido.setItems(itemsPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converteritems(Pedido pedido,List<ItemPedidoDTO> items) {
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
        }

        return items
                .stream()
                .map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository
                    .findById(idProduto)
                    .orElseThrow(
                            () -> new RegraNegocioException(
                                    "Código do produto invalido: " + idProduto
                            ));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;

        }).collect(Collectors.toList());
    }
}
