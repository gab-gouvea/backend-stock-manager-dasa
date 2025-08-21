package br.com.estoque.dasa.modules.product.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductConsumer {

    @Autowired
    private RestTemplate restTemplate;
}
