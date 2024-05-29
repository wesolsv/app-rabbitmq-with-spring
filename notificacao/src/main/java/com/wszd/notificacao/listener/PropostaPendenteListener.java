package com.wszd.notificacao.listener;

import com.wszd.notificacao.constante.MensagemConstante;
import com.wszd.notificacao.domain.Proposta;
import com.wszd.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta){
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE);
        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
