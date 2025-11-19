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

    public Iterable<ProdutoModelo> listar(){
        return er.findAll(); 
    }

    public Optional<ProdutoModelo> buscarProduto(Long id) {
        return er.findById(id);
    }

}