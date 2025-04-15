package com.github.acnaweb.study_apiw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.acnaweb.study_apiw.dto.ProdutoRequestCreate;
import com.github.acnaweb.study_apiw.dto.ProdutoRequestUpdate;
import com.github.acnaweb.study_apiw.model.Produto;
import com.github.acnaweb.study_apiw.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Optional<Produto> update(Long id, ProdutoRequestUpdate dto) {
        return produtoRepository.findById(id)
            .map(produto -> {
                produto.setNome(dto.getNome());
                return produtoRepository.save(produto);
            });
    }

    public Produto save(ProdutoRequestCreate dto) {     
        
        Produto produto  = new Produto();
        produto.setNome(dto.getNome());
        
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        Optional<Produto> opt = produtoRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

}
