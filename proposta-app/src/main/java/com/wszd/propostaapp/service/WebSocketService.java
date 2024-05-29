package com.wszd.propostaapp.service;

import com.wszd.propostaapp.DTO.PropostaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void notificar(PropostaResponseDto proposta){
        template.convertAndSend("/propostas", proposta);
    }
}
