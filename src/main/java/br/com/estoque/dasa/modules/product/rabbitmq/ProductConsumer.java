package br.com.estoque.dasa.modules.product.rabbitmq;

import br.com.estoque.dasa.modules.product_log.service.DataRemoval;
import br.com.estoque.dasa.modules.product_log.service.WrapperStockOut;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductConsumer {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "queue.product.remove")
    public void receiveMessage(String message) {
        try {
            WrapperStockOut data = objectMapper.readValue(message, WrapperStockOut.class);

            restTemplate.postForObject("http://localhost:8080/products/removal", data, String.class);
            System.out.println("mensagem processada" + data);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
