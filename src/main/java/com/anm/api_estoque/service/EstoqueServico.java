package com.anm.api_estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anm.api_estoque.model.ProdutoModelo;
import com.anm.api_estoque.repository.ProdutoRepositorio;

@Service
public class EstoqueServico{

    @Autowired
    private ProdutoRepositorio pr;

    public Iterable<ProdutoModelo> listar(){
        return pr.findAll();
    }
}
