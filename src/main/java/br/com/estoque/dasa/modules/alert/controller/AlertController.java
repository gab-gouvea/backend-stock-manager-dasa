package br.com.estoque.dasa.modules.alert.controller;

import br.com.estoque.dasa.modules.alert.repository.AlertRepository;
import br.com.estoque.dasa.modules.alert.service.DataListAlert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertRepository repository;

    @GetMapping
    public List<DataListAlert> list() {
        return repository.findAll().stream().map(DataListAlert::new).toList();
    }

    @PutMapping("/status/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        var alert = repository.getReferenceById(id);
        alert.updateStatus();

        return ResponseEntity.ok("Alerta solucionado!");
    }

}
