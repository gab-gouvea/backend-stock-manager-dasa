package br.com.estoque.dasa.modules.product.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public Queue queueProduct() {
        return new Queue("fila.produto.atualizacao", true);
    }

}
