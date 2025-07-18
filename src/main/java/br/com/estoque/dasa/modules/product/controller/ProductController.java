package br.com.estoque.dasa.modules.product.controller;


import br.com.estoque.dasa.modules.alert.entity.Alert;
import br.com.estoque.dasa.modules.alert.repository.AlertRepository;
import br.com.estoque.dasa.modules.alert.service.EnumTipo;
import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
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

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateProduct data) {
        if (repository.existsByName(data.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este produto já foi cadastrado!");
        }

        if (!categoryRepository.existsById(data.categoryId())) {
            return ResponseEntity.notFound().build();
        }

        var category = categoryRepository.getReferenceById(data.categoryId());
        repository.save(new Product(data.name(), data.description(), data.quantity(), data.minQuantity(), category));

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

    @PostMapping("/removal/{id}")
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

        if (product.getQuantity() <= product.getMinQuantity()) {
            var alert = new Alert(product, EnumTipo.QUANTIDADE_MINIMA, true, "Quantidade desse produto chegou no limite, compre mais!");
            alertRepository.save(alert);
        }

        var log = new ProductLog(data.action(), data.quantity(), data.cpf(), product);
        logRepository.save(log);

        return ResponseEntity.ok("Estoque atualizado e log gerado!");
    }

    @PostMapping("/join/{id}")
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

        if (product.getQuantity() <= product.getMinQuantity()) {
            var alert = new Alert(product, EnumTipo.QUANTIDADE_MINIMA, true, "Quantidade desse produto chegou no limite, compre mais!");
            alertRepository.save(alert);
        }

        var log = new ProductLog(data.action(), data.quantity(), data.cpf(), product);
        logRepository.save(log);

        return ResponseEntity.ok("Estoque atualizado e log gerado!");
    }

}
