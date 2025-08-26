package br.com.estoque.dasa.modules.product_log.entity;


import br.com.estoque.dasa.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "product_log")
@Table(name = "product_logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class ProductLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = true)
    private String cpf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public ProductLog(Long quantity, String cpf, Product product) {
        this.quantity = quantity;
        this.cpf = cpf;
        this.product = product;
    }

    public ProductLog(Long quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
}

