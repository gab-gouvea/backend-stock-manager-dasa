package br.com.estoque.dasa.modules.product.entity;

import br.com.estoque.dasa.modules.category.entity.Category;
import br.com.estoque.dasa.modules.product.service.DataAttProduct;
import br.com.estoque.dasa.modules.product_log.service.DataJoin;
import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import br.com.estoque.dasa.modules.test.controller.TestRabbit;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "products")
@Entity
@Getter
@Setter
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

            throw new IllegalArgumentException("Estoque insuficiente: ");
        }
        this.quantity -= data.quantity();
    }

    public void stockIn(@Valid DataJoin data) {
        this.quantity += data.quantity();
    }

}
