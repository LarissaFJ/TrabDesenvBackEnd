package com.example.trabdesenvbackend.service;

import com.example.trabdesenvbackend.model.Pedido;
import com.example.trabdesenvbackend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> consultarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido criar(Pedido pedido) {

        if (clienteService.consultarPorId(pedido.getClienteId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        if (produtoService.consultarPorId(pedido.getProdutoId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        return pedidoRepository.save(pedido);
    }

    public void apagar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
