package br.com.estoque.dasa.modules.category.service;

import br.com.estoque.dasa.modules.category.entity.Category;
import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product.service.DataCountCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ResponseEntity<?> create(DataCreateCategory data) {
        if (repository.existsByName(data.name()) || repository.existsByColor(data.color())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome ou cor já estão cadastrados!");
        }
        repository.save(new Category(data));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public List<DataListCategory> listAll() {
        return repository.findAll().stream().map(DataListCategory::new).toList();
    }

    public List<DataCountCategory> count() {
        return productRepository.fetchProductByCategory();
    }

    @Transactional
    public ResponseEntity<?> update(DataAttCategory data) {
        if (!repository.existsById(data.id())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrado.");
        }
        if (repository.existsByName(data.name()) || repository.existsByColor(data.color())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome ou cor já estão cadastrados!");
        }
        var category = repository.getReferenceById(data.id());
        category.updateValues(data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");
    }

    @Transactional
    public ResponseEntity<?> delete(String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrado.");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
