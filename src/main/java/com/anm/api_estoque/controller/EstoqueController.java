package com.anm.api_estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.anm.api_estoque.model.ProdutoModelo;

@RestController
@CrossOrigin("*")
public class EstoqueController {

    @Autowired
    private com.anm.api_estoque.service.EstoqueServico es;

    @GetMapping("/")
    public void testeApi() {
        System.out.println("A API está funcionando!");
    }

    @GetMapping("/listar")
    public Iterable<ProdutoModelo> listar() {
        return es.listar();
    }
}