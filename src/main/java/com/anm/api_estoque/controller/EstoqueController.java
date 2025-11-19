package com.anm.api_estoque.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anm.api_estoque.model.ProdutoModelo;


@RestController
@CrossOrigin("*")
@RequestMapping("/listar")
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

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModelo> buscarPorId(@PathVariable Long id) {
        Optional<ProdutoModelo> produto = es.buscarProduto(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}