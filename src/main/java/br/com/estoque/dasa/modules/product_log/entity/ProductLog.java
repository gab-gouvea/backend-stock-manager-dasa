package br.com.estoque.dasa.modules.product_log.entity;


import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import br.com.estoque.dasa.modules.product_log.service.EnumAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private String withdrawBy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumAction action;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public ProductLog(Long quantity, String withdrawBy, Product product, EnumAction action) {
        this.quantity = quantity;
        this.withdrawBy = withdrawBy;
        this.product = product;
        this.action = action;
    }

    public ProductLog(Long quantity, Product product, EnumAction action) {
        this.quantity = quantity;
        this.product = product;
        this.action = action;
    }
}

