package com.wszd.propostaapp.service;

import com.wszd.propostaapp.entity.Proposta;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoRabbitService {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoRabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notificar(Proposta proposta, String exchange, MessagePostProcessor messagePostProcessor){
        rabbitTemplate.convertAndSend(exchange, "", proposta, messagePostProcessor);

    }
    public void notificar(Proposta proposta, String exchange){
        rabbitTemplate.convertAndSend(exchange, "", proposta);

    }
}
