package br.com.estoque.dasa.modules.alert.repository;

import br.com.estoque.dasa.modules.alert.entity.Alert;
import br.com.estoque.dasa.modules.alert.service.EnumTipo;
import br.com.estoque.dasa.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert,String> {
    long countByStatusTrue();
    List<Alert> findByProductAndStatusTrue(Product product);

    void deleteByProductId(String id);
}

