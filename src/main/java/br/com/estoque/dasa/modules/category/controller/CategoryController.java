package br.com.estoque.dasa.modules.category.controller;

import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
import br.com.estoque.dasa.modules.category.service.Category;
import br.com.estoque.dasa.modules.category.service.DataAttCategory;
import br.com.estoque.dasa.modules.category.service.DataCreateCategory;
import br.com.estoque.dasa.modules.category.service.DataListCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @PostMapping
    @Transactional
    public void create(@RequestBody DataCreateCategory data) {
        repository.save(new Category(data));
    }

    @GetMapping
    public List<DataListCategory> list() {
        return repository.findAll().stream().map(DataListCategory::new).toList();
    }

    @PutMapping
    @Transactional
    public Category update(@RequestBody DataAttCategory data) {
       var category =  repository.getReferenceById(data.id());
       category.updateValues(data);
       return category;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
