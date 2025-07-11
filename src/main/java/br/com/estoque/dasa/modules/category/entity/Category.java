package br.com.estoque.dasa.modules.category.entity;

import br.com.estoque.dasa.modules.category.service.DataAttCategory;
import br.com.estoque.dasa.modules.category.service.DataCreateCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "categories")
@Entity(name = "category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String color;

    public Category(DataCreateCategory data) {
        this.name = data.name();
        this.color = data.color();
    }

    public void updateValues(DataAttCategory data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.color() != null) {
            this.color = data.color();
        }
    }
}
