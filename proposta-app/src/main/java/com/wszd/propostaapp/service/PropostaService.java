package com.wszd.propostaapp.service;

import com.wszd.propostaapp.DTO.PropostaRequestDto;
import com.wszd.propostaapp.DTO.PropostaResponseDto;
import com.wszd.propostaapp.entity.Proposta;
import com.wszd.propostaapp.mapper.PropostaMapper;
import com.wszd.propostaapp.repository.PropostaRepository;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository repository;
    private final NotificacaoRabbitService notificacaoRabbitService;
    private String exchange;

    public PropostaService(PropostaRepository repository,
                           NotificacaoRabbitService notificacaoRabbitService,
                           @Value("${rabbitmq.proposta-pendente.exchange}") String exchange) {
        this.repository = repository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    public PropostaResponseDto criar(PropostaRequestDto propostaRequestDto){
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(propostaRequestDto);
        repository.save(proposta);

        int prioridade = proposta.getUsuario().getRenda() > 10000 ? 10 : 5;
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setPriority(prioridade);
            return message;
        };

        notificarRabbitMQ(proposta, messagePostProcessor);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

    public void notificarRabbitMQ(Proposta proposta, MessagePostProcessor messagePostProcessor){
        try{
            notificacaoRabbitService.notificar(proposta, exchange, messagePostProcessor);
        }catch (RuntimeException ex){
            proposta.setIntegrada(false);
            repository.save(proposta);
        }
    }

    public List<PropostaResponseDto> listarPropostas() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(repository.findAll());
    }
}
