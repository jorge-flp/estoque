package com.anm.api_estoque.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anm.api_estoque.model.ProdutoModelo;
import com.anm.api_estoque.repository.EstoqueRepositorio;

@Service
public class EstoqueServico {

    @Autowired
    private EstoqueRepositorio er;

    public Iterable<ProdutoModelo> listar() {
        return er.findAll();
    }

    public Optional<ProdutoModelo> buscarProduto(Long id) {
        return er.findById(id);
    }

    public ProdutoModelo cadastrar(ProdutoModelo produto) {
        return er.save(produto);
    }

    public Optional<ProdutoModelo> alterar(Long id, ProdutoModelo produto) {
        if (er.existsById(id)) {
            produto.setId(id); // Ensure ID is set for update
            return Optional.of(er.save(produto));
        }
        return Optional.empty();
    }

    public boolean remover(Long id) {
        if (er.existsById(id)) {
            er.deleteById(id);
            return true;
        }
        return false;
    }

}