package br.com.estoque.dasa.modules.product_log.repository;

import br.com.estoque.dasa.modules.product_log.entity.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLogRepository extends JpaRepository<ProductLog, String> {
}
