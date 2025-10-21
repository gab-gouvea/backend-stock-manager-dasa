package br.com.estoque.dasa.modules.alert.service;

import br.com.estoque.dasa.modules.alert.repository.AlertRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository repository;

    public List<DataListAlert> list() {
        return repository.findAll().stream().map(DataListAlert::new).toList();
    }

    @Transactional
    public ResponseEntity<?> updateStatus(String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta não encontrado.");
        }

        var alert = repository.getReferenceById(id);
        alert.updateStatus();

        return ResponseEntity.ok("Alerta solucionado!");
    }
}
