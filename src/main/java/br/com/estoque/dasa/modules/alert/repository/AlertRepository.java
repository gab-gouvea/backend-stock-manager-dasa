package br.com.estoque.dasa.modules.alert.repository;

import br.com.estoque.dasa.modules.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert,String> {
}

