package br.com.estoque.dasa.modules.product_log.controller;



import br.com.estoque.dasa.modules.product_log.service.DataListLogs;
import br.com.estoque.dasa.modules.product_log.service.ProductLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product_logs")
public class ProductLogController{

    @Autowired
    private ProductLogService productLogService;

    @GetMapping
    public List<DataListLogs> list() {
        return productLogService.list();
    }
}




