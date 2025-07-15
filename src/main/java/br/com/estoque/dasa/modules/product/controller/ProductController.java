package br.com.estoque.dasa.modules.product.controller;


import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product.service.DataAttProduct;
import br.com.estoque.dasa.modules.product.service.DataCreateProduct;
import br.com.estoque.dasa.modules.product.service.DataListProduct;
import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product_log.entity.ProductLog;
import br.com.estoque.dasa.modules.product_log.repository.ProductLogRepository;
import br.com.estoque.dasa.modules.product_log.service.DataJoin;
import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductLogRepository logRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateProduct data) {
        if (repository.existsByName(data.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este produto já foi cadastrado!");
        }
        repository.save(new Product(data));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Page<DataListProduct> list(Pageable pagination) {
        return repository.findAll(pagination).map(DataListProduct::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid DataAttProduct data) {
        if (!repository.existsById(data.id())) {
            return ResponseEntity.notFound().build();
        }
        var product = repository.getReferenceById(data.id());
        product.updateValues(data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/removal")
    @Transactional
    public ResponseEntity<?> stockOut(
            @PathVariable String id,
            @RequestBody @Valid DataRemoval data
    ) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Product product = repository.getReferenceById(id);

        try {
            product.stockOut(data);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }

        var log = new ProductLog(data.action(), data.quantity(), data.cpf(), product);
        logRepository.save(log);

        return ResponseEntity.ok("Estoque atualizado e log gerado!");
    }

    @PostMapping("/{id}/join")
    @Transactional
    public ResponseEntity<?> stockIn(
        @PathVariable String id,
        @RequestBody @Valid DataJoin data
   ) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        var product = repository.getReferenceById(id);
        product.stockIn(data);

        var log = new ProductLog(data.action(), data.quantity(), data.cpf(), product);
        logRepository.save(log);

        return ResponseEntity.ok("Estoque atualizado e log gerado!");
    }

}
