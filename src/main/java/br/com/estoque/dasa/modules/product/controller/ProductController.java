package br.com.estoque.dasa.modules.product.controller;


import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product.service.DataAttProduct;
import br.com.estoque.dasa.modules.product.service.DataCreateProduct;
import br.com.estoque.dasa.modules.product.service.DataListProduct;
import br.com.estoque.dasa.modules.product.service.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping
    @Transactional
    public void create(@RequestBody DataCreateProduct data) {
        repository.save(new Product(data));
    }

    @GetMapping
    public Page<DataListProduct> list(Pageable pagination) {
        return repository.findAll(pagination).map(DataListProduct::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody DataAttProduct data) {
        var product = repository.getReferenceById(data.id());
        product.updateValues(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
