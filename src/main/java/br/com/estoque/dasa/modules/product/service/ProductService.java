package br.com.estoque.dasa.modules.product;

import br.com.estoque.dasa.modules.alert.entity.Alert;
import br.com.estoque.dasa.modules.alert.repository.AlertRepository;
import br.com.estoque.dasa.modules.alert.service.EnumTipo;
import br.com.estoque.dasa.modules.category.repository.CategoryRepository;
import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product.service.*;
import br.com.estoque.dasa.modules.product_log.entity.ProductLog;
import br.com.estoque.dasa.modules.product_log.repository.ProductLogRepository;
import br.com.estoque.dasa.modules.product_log.service.DataJoin;
import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import br.com.estoque.dasa.modules.product_log.service.EnumAction;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ProductLogRepository logRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void stockOut(List<DataRemoval> itens, String withdrawBy) {

        for (DataRemoval i : itens) {
            if (!repository.existsByName(i.productName())) {
                continue;
            }

            Product product = repository.getReferenceByName(i.productName());

            try {
                if(product.getQuantity() < i.quantity()) {
                    rabbitTemplate.convertAndSend("stock", "remove.error", "Erro ao retirar item: " + i.productName());
                }
                product.setQuantity(product.getQuantity() - i.quantity());
            } catch (IllegalArgumentException e) {
                continue;
            }

            if (product.getQuantity() <= product.getMinQuantity()) {
                var alert = new Alert(product, EnumTipo.QUANTIDADE_MINIMA, true,
                        "Quantidade desse produto chegou no limite, compre mais!");
                alertRepository.save(alert);
            }

            var log = new ProductLog(i.quantity(), withdrawBy, product, EnumAction.RETIRADA_ESTOQUE);
            logRepository.save(log);
        }
    }

    @Transactional
    public ResponseEntity<?> create(DataCreateProduct data) {
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

    public List<DataListProduct> list() {
        return repository.findAll().stream().map(DataListProduct::new).toList();
    }

    @Transactional
    public ResponseEntity<?> update(DataAttProduct data) {
        if (!repository.existsById(data.id())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        var product = repository.getReferenceById(data.id());
        product.updateValues(data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");
    }

    @Transactional
    public ResponseEntity<?> delete(String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        alertRepository.deleteByProductId(id);
        logRepository.deleteByProductId(id);
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<?> stockIn(String id, DataJoin data) {
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

    public ResponseEntity<?> counts() {
        long total = repository.count();
        long minimum = alertRepository.countByStatusTrue();
        long join = logRepository.countByAction(EnumAction.ENTRADA_ESTOQUE);
        long removal = logRepository.countByAction(EnumAction.RETIRADA_ESTOQUE);
        DataCounts counts = new DataCounts(total, minimum, join, removal);
        return ResponseEntity.ok(counts);
    }
}
