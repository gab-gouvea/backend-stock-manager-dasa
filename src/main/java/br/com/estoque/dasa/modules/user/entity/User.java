package br.com.estoque.dasa.modules.user.entity;

import br.com.estoque.dasa.modules.user.service.DataAttUser;
import br.com.estoque.dasa.modules.user.service.DataCreateUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(DataCreateUser data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
    }

    public void updateValues(DataAttUser data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.password() != null) {
            this.password = data.password();
        }
    }


}
