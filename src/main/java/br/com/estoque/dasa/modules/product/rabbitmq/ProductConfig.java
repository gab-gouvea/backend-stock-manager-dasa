package br.com.estoque.dasa.modules.product.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public Queue queueProductRemove() {
        return new Queue("queue.product.remove", true);
    }

    @Bean
    public DirectExchange stockExchange() {
        return new DirectExchange("stock");
    }

    @Bean
    public Binding bindingQueueProductRemove(Queue queueProductRemove, DirectExchange stockExchange ) {
        return BindingBuilder.bind(queueProductRemove)
                .to(stockExchange)
                .with("remove");
    }
}
