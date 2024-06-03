package com.wszd.propostaapp.agendador;

import com.wszd.propostaapp.entity.Proposta;
import com.wszd.propostaapp.repository.PropostaRepository;
import com.wszd.propostaapp.service.NotificacaoRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private String exchange;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    public PropostaSemIntegracao(PropostaRepository propostaRepository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                @Value("${rabbitmq.proposta-pendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao(){
        propostaRepository.findAllPropostasByIntegradaIsFalse().forEach(proposta ->{
            try{
                notificacaoRabbitService.notificar(proposta, exchange);
                propostaRepository.atualizaStatusIntegrada(proposta.getId(), true);
            }catch (RuntimeException ex){
                logger.error(ex.getMessage());
            }

        });
    }
}
