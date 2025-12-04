package com.anm.api_estoque.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anm.api_estoque.model.ProdutoModelo;
import com.anm.api_estoque.service.EstoqueServico;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produtos") // Changed from /listar to a more RESTful path
public class EstoqueController {

    @Autowired
    private EstoqueServico es;

    @GetMapping("/")
    public ResponseEntity<String> testeApi() {
        return ResponseEntity.ok("A API está funcionando!");
    }

    @GetMapping
    public ResponseEntity<Iterable<ProdutoModelo>> listar() {
        return ResponseEntity.ok(es.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModelo> buscarPorId(@PathVariable Long id) {
        Optional<ProdutoModelo> produto = es.buscarProduto(id);
        return produto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoModelo> cadastrar(@Valid @RequestBody ProdutoModelo produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(es.cadastrar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModelo> alterar(@PathVariable Long id, @Valid @RequestBody ProdutoModelo produto) {
        Optional<ProdutoModelo> produtoAtualizado = es.alterar(id, produto);
        return produtoAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (es.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}