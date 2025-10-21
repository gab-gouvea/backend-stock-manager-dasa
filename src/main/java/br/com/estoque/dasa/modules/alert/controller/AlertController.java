package br.com.estoque.dasa.modules.alert.controller;

import br.com.estoque.dasa.modules.alert.service.AlertService;
import br.com.estoque.dasa.modules.alert.service.DataListAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping
    public List<DataListAlert> list() {
        return alertService.list();
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> update(@PathVariable String id) {
        return alertService.updateStatus(id);
    }

}
