package br.com.estoque.dasa.modules.test.controller;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rabbit")
public class TestRabbit {


    private final RabbitTemplate rabbitTemplate;

    public TestRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/send")
    @Transactional
    public void send(@RequestBody String message){
        rabbitTemplate.convertAndSend("stock", "remove", message);
        System.out.println("mensagem processada" + message);

    }

    public void sendError() {

    }
}
