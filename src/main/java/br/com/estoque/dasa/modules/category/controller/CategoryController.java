package br.com.estoque.dasa.modules.category.controller;

import br.com.estoque.dasa.modules.category.service.CategoryService;
import br.com.estoque.dasa.modules.category.service.DataAttCategory;
import br.com.estoque.dasa.modules.category.service.DataCreateCategory;
import br.com.estoque.dasa.modules.category.service.DataListCategory;
import br.com.estoque.dasa.modules.product.service.DataCountCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateCategory data) {
        return categoryService.create(data);
    }

    @GetMapping("/all")
    public List<DataListCategory> list() {
        return categoryService.listAll();
    }

    @GetMapping
    public List<DataCountCategory> count() {
        return categoryService.count();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataAttCategory data) {
        return categoryService.update(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return categoryService.delete(id);
    }

}
