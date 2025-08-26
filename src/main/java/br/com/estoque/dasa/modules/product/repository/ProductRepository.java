package br.com.estoque.dasa.modules.product.repository;

import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product.service.DataCountCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    boolean existsByName(String name);

    @Query("SELECT new br.com.estoque.dasa.modules.product.service.DataCountCategory(p.category.name, COUNT(p)) " +
            "FROM product p GROUP BY p.category.name")
    List<DataCountCategory> countProductByCategory();
}
