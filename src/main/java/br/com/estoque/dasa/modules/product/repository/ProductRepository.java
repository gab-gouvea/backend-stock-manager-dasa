package br.com.estoque.dasa.modules.product.repository;

import br.com.estoque.dasa.modules.product.service.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
