package br.com.estoque.dasa.modules.product.entity;

import br.com.estoque.dasa.modules.category.entity.Category;
import br.com.estoque.dasa.modules.product.service.DataAttProduct;
import br.com.estoque.dasa.modules.product.service.DataCreateProduct;
import br.com.estoque.dasa.modules.product_log.service.DataJoin;
import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "products")
@Entity(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "min_quantity", nullable = false)
    private Long minQuantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(String name, String description, Long quantity, Long minQuantity, Category category) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.category = category;
    }

    public void updateValues(@Valid DataAttProduct data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.description() != null) {
            this.description = data.description();
        }

    }

    public void stockOut(@Valid DataRemoval data) {
        if (data.quantity() > this.quantity) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        this.quantity -= data.quantity();
    }

    public void stockIn(@Valid DataJoin data) {
        this.quantity += data.quantity();
    }
}
