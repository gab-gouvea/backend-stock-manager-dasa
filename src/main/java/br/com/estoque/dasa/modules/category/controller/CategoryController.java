package br.com.estoque.dasa.modules.category.controller;

import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
import br.com.estoque.dasa.modules.category.entity.Category;
import br.com.estoque.dasa.modules.category.service.DataAttCategory;
import br.com.estoque.dasa.modules.category.service.DataCreateCategory;
import br.com.estoque.dasa.modules.category.service.DataListCategory;
import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product.service.DataCountCategory;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateCategory data) {
        if (repository.existsByName(data.name()) || repository.existsByColor(data.color())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome ou cor já estão cadastrados!");
        }
        repository.save(new Category(data));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public List<DataListCategory> list() {
        return repository.findAll().stream().map(DataListCategory::new).toList();
    }

    @GetMapping
    public ResponseEntity<List<DataCountCategory>> count() {
        List<DataCountCategory> produtosCate = productRepository.countProductByCategory();
        return ResponseEntity.ok(produtosCate);
    }


    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid DataAttCategory data) {
        if (!repository.existsById(data.id())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrado.");
        }
        if (repository.existsByName(data.name()) || repository.existsByColor(data.color())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome ou cor já estão cadastrados!");
        }
       var category =  repository.getReferenceById(data.id());
       category.updateValues(data);
       return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrado.");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
