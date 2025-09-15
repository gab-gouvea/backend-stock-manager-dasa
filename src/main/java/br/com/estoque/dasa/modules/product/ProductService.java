package br.com.estoque.dasa.modules.product;

import br.com.estoque.dasa.modules.alert.entity.Alert;
import br.com.estoque.dasa.modules.alert.repository.AlertRepository;
import br.com.estoque.dasa.modules.alert.service.EnumTipo;
import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product.repository.ProductRepository;
import br.com.estoque.dasa.modules.product_log.entity.ProductLog;
import br.com.estoque.dasa.modules.product_log.repository.ProductLogRepository;
import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import br.com.estoque.dasa.modules.product_log.service.EnumAction;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public void stockOut(List<DataRemoval> itens) {

        for (DataRemoval i : itens) {
            if (!repository.existsByName(i.productName())) {
                System.out.println("NOME NAO EXISTE");
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

            var log = new ProductLog(i.quantity(), i.withdrawnBy(), product, EnumAction.RETIRADA_ESTOQUE);
            logRepository.save(log);
        }
    }
}
