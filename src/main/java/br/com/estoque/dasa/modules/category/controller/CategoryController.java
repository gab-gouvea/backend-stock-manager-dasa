package br.com.estoque.dasa.modules.category.controller;

import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
import br.com.estoque.dasa.modules.category.entity.Category;
import br.com.estoque.dasa.modules.category.service.DataAttCategory;
import br.com.estoque.dasa.modules.category.service.DataCreateCategory;
import br.com.estoque.dasa.modules.category.service.DataListCategory;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateCategory data) {
        if (repository.existsByName(data.name()) || repository.existsByColor(data.color())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome ou cor já estão cadastrados!");
        }
        repository.save(new Category(data));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<DataListCategory> list() {
        return repository.findAll().stream().map(DataListCategory::new).toList();
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
