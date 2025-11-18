package com.anm.api_estoque.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anm.api_estoque.model.ProdutoModelo;

@Repository
public interface EstoqueRepositorio extends CrudRepository<ProdutoModelo, Long> {

}
