package br.com.estoque.dasa.modules.product_log.repository;

import br.com.estoque.dasa.modules.product_log.entity.ProductLog;
import br.com.estoque.dasa.modules.product_log.service.EnumAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLogRepository extends JpaRepository<ProductLog, String> {
    List<ProductLog> findByAction(EnumAction action);
    long countByAction(EnumAction action);
}
