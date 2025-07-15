package br.com.estoque.dasa.modules.product_log.controller;


import br.com.estoque.dasa.modules.product.service.DataListProduct;
import br.com.estoque.dasa.modules.product_log.repository.ProductLogRepository;
import br.com.estoque.dasa.modules.product_log.service.DataListLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product_logs")
public class ProductLogController {

    @Autowired
    private ProductLogRepository repository;

    @GetMapping
    public List<DataListLogs> list() {
        return repository.findAll().stream().map(DataListLogs::new).toList();
    }
}
