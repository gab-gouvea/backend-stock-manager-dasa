package br.com.estoque.dasa.modules.product.controller;


import br.com.estoque.dasa.modules.alert.repository.AlertRepository;
import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
import br.com.estoque.dasa.modules.product.ProductService;
import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product.service.*;
import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product_log.entity.ProductLog;
import br.com.estoque.dasa.modules.product_log.repository.ProductLogRepository;
import br.com.estoque.dasa.modules.product_log.service.DataJoin;
import br.com.estoque.dasa.modules.product_log.service.EnumAction;
import br.com.estoque.dasa.modules.product_log.service.WrapperStockOut;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateProduct data) {
        if (repository.existsByName(data.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este produto já foi cadastrado!");
        }

        if (!categoryRepository.existsById(data.categoryId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }

        var category = categoryRepository.getReferenceById(data.categoryId());
        repository.save(new Product(data.name(), data.description(), data.quantity(), data.minQuantity(), category));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<DataListProduct> list() {
        return repository.findAll().stream().map(DataListProduct::new).toList();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid DataAttProduct data) {
        if (!repository.existsById(data.id())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        var product = repository.getReferenceById(data.id());
        product.updateValues(data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        alertRepository.deleteByProductId(id);
        logRepository.deleteByProductId(id);
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/removal")
    @Transactional
    public ResponseEntity<?> stockOut(@RequestBody @Valid WrapperStockOut request) {
        productService.stockOut(request.itens(), request.withdrawBy());
        return ResponseEntity.ok("Estoque atualizado e log gerado!");
    }
//    @Transactional
//    public ResponseEntity<?> stockOut(
//            @RequestBody @Valid WrapperStockOut request
//            ) {
//
//        List<DataRemoval> itens = request.itens();
//
//        for  (DataRemoval i : itens) {
//            if (!repository.existsByName(i.productName())) {
//                continue;
//            }
//
//            Product product = repository.getReferenceByName(i.productName());
//
//            try {
//                productService.stockOut();
//            } catch (IllegalArgumentException error) {
//                continue;
//            }
//
//            if (product.getQuantity() <= product.getMinQuantity()) {
//                var alert = new Alert(product, EnumTipo.QUANTIDADE_MINIMA, true, "Quantidade desse produto chegou no limite, compre mais!");
//                alertRepository.save(alert);
//            }
//
//            var log = new ProductLog(i.quantity(), i.withdrawnBy(), product, EnumAction.RETIRADA_ESTOQUE);
//            logRepository.save(log);
//        }
//
//        return ResponseEntity.ok("Estoque atualizado e log gerado!");
//    }

    @PostMapping("/join/{id}")
    @Transactional
    public ResponseEntity<?> stockIn(
        @PathVariable String id,
        @RequestBody @Valid DataJoin data
   ) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        var product = repository.getReferenceById(id);
        product.stockIn(data);

        if (product.getQuantity() > product.getMinQuantity()) {
            alertRepository.findByProductAndStatusTrue(product)
                    .forEach(alert -> {
                        alert.updateStatus();
                        alertRepository.save(alert);
                    });
        }

        var log = new ProductLog(data.quantity(), product, EnumAction.ENTRADA_ESTOQUE);
        logRepository.save(log);

        return ResponseEntity.ok("Estoque atualizado e log gerado!");
    }

    @GetMapping("/counts")
    public ResponseEntity<?> counts() {
        long total = repository.count();
        long minimum = alertRepository.countByStatusTrue();
        long join = logRepository.countByAction(EnumAction.ENTRADA_ESTOQUE);
        long removal = logRepository.countByAction(EnumAction.RETIRADA_ESTOQUE);
        DataCounts counts = new DataCounts(total, minimum, join, removal);
        return ResponseEntity.ok(counts);
    }
}
