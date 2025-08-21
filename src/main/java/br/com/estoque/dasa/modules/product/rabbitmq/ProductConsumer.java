package br.com.estoque.dasa.modules.product.rabbitmq;

import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Component
public class ProductConsumer {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DataSource dataSource;

    @RabbitListener(queues = "fila.produto.atualizacao")
    public void receiveMessage(String message) {
        try {
            DataRemoval data = objectMapper.readValue(message, DataRemoval.class);

            restTemplate.postForObject("http://localhost:8080/products/removal", data, String.class);

            System.out.println("mensagem processada" + data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }
}
