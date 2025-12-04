package com.anm.api_estoque.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.anm.api_estoque.model.ProdutoModelo;
import com.anm.api_estoque.service.EstoqueServico;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EstoqueController.class)
public class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstoqueServico estoqueServico;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoModelo produto;

    @BeforeEach
    void setUp() {
        produto = new ProdutoModelo();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setModelo("Modelo X");
        produto.setDescricao("Descrição Teste");
    }

    @Test
    void testListar() throws Exception {
        when(estoqueServico.listar()).thenReturn(Arrays.asList(produto));

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto Teste"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(estoqueServico.buscarProduto(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    void testCadastrar() throws Exception {
        when(estoqueServico.cadastrar(any(ProdutoModelo.class))).thenReturn(produto);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    void testAlterar() throws Exception {
        when(estoqueServico.alterar(eq(1L), any(ProdutoModelo.class))).thenReturn(Optional.of(produto));

        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    void testRemover() throws Exception {
        when(estoqueServico.remover(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testRemoverNaoEncontrado() throws Exception {
        when(estoqueServico.remover(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNotFound());
    }
}
