package br.com.estoque.dasa.modules.product_log.service;

import br.com.estoque.dasa.modules.product_log.repository.ProductLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductLogService {

    @Autowired
    private ProductLogRepository repository;

    public List<DataListLogs> list() {
        return repository.findByAction(EnumAction.RETIRADA_ESTOQUE).stream().map(DataListLogs::new).toList();
    }
}
