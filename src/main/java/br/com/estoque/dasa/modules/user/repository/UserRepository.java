package br.com.estoque.dasa.modules.user.repository;

import br.com.estoque.dasa.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
