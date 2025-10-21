package br.com.estoque.dasa.modules.product.controller;


import br.com.estoque.dasa.modules.product.ProductService;
import br.com.estoque.dasa.modules.product.service.*;
import br.com.estoque.dasa.modules.product_log.service.DataJoin;
import br.com.estoque.dasa.modules.product_log.service.WrapperStockOut;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateProduct data) {
        return productService.create(data);
    }

    @GetMapping
    public List<DataListProduct> list() {
        return productService.list();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataAttProduct data) {
        return productService.update(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return productService.delete(id);
    }

    @PostMapping("/removal")
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
    public ResponseEntity<?> stockIn(
        @PathVariable String id,
        @RequestBody @Valid DataJoin data
   ) {
        return productService.stockIn(id, data);
    }

    @GetMapping("/counts")
    public ResponseEntity<?> counts() {
        return productService.counts();
    }
}
