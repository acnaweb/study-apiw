package com.github.acnaweb.study_apiw.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.acnaweb.study_apiw.dto.ProdutoRequestCreate;
import com.github.acnaweb.study_apiw.dto.ProdutoRequestUpdate;
import com.github.acnaweb.study_apiw.dto.ProdutoResponse;
import com.github.acnaweb.study_apiw.model.Produto;
import com.github.acnaweb.study_apiw.service.ProdutoService;

@RestController
@RequestMapping("produtos")
public class ControllerProduto {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> create(@RequestBody ProdutoRequestCreate dto) {        
        Produto produto = produtoService.save(dto);
        ProdutoResponse response = new ProdutoResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        return  ResponseEntity.status(201).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable Long id,
                        @RequestBody ProdutoRequestUpdate dto) {
        return produtoService.update(id, dto)
            .map(produto -> {
                ProdutoResponse response = new ProdutoResponse();
                response.setId(produto.getId());
                response.setNome(produto.getNome());
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
   
    @GetMapping("{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        return produtoService.findById(id)
            .map(produto -> {
                ProdutoResponse response = new ProdutoResponse();
                response.setId(produto.getId());
                response.setNome(produto.getNome());                
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> findAll() {

        List<ProdutoResponse> lista = produtoService.findAll()
            .stream()
            .map(produto -> {
                ProdutoResponse response = new ProdutoResponse();
                response.setId(produto.getId());
                response.setNome(produto.getNome());                
                return response;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (produtoService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }                
    }

}
