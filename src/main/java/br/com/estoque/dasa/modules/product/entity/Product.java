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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(@Valid DataCreateProduct data) {
        this.name = data.name();
        this.description = data.description();
        this.quantity = data.quantity();
        this.minQuantity = data.minQuantity();
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

        if (this.quantity < this.minQuantity) {
            System.out.println("Implementar alerta");
        }
    }

    public void stockIn(@Valid DataJoin data) {
        this.quantity += data.quantity();

        if (this.quantity < this.minQuantity) {
            System.out.println("Implementar alerta");
        }
// a quantidade ainda pode ser menor q o minimo para o alerta, mesmo depois de entrar produto no estoque
    }
}
