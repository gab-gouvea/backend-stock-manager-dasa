package br.com.estoque.dasa.modules.category.repository;

import br.com.estoque.dasa.modules.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
