package br.com.estoque.dasa.modules.alert.entity;


import br.com.estoque.dasa.modules.alert.service.EnumTipo;
import br.com.estoque.dasa.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "alerts")
@Entity(name = "alert")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Enumerated(EnumType.STRING)
    private EnumTipo tipo;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private String message;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Alert(Product product, EnumTipo tipo, boolean status, String message) {
        this.product = product;
        this.tipo = tipo;
        this.status = status;
        this.message = message;
    }

    public void updateStatus() {
        this.status = false;
    }
}
