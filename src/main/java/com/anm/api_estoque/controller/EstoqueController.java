package com.anm.api_estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anm.api_estoque.model.ProdutoModelo;
import com.anm.api_estoque.service.EstoqueServico;

@RestController
@CrossOrigin("*")
public class EstoqueController{

    @Autowired
    private EstoqueServico es;

    @GetMapping("/")
    public String getMethodName(@RequestParam String param) {
        return new String("A API está funcionando!");
    }
    
    @GetMapping("/listar")
    public Iterable<ProdutoModelo> listar(){
        return es.listar();
    }
    
    
}
