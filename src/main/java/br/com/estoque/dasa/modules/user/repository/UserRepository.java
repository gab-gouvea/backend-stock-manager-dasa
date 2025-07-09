package br.com.estoque.dasa.modules.user.repository;

import br.com.estoque.dasa.modules.user.service.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
