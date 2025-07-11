package br.com.estoque.dasa.modules.product.entity;

import br.com.estoque.dasa.modules.category.entity.Category;
import br.com.estoque.dasa.modules.product.service.DataAttProduct;
import br.com.estoque.dasa.modules.product.service.DataCreateProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    public Product(DataCreateProduct data) {
        this.name = data.name();
        this.description = data.description();
    }

    public void updateValues(DataAttProduct data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.description() != null) {
            this.description = data.description();
        }

    }
}
