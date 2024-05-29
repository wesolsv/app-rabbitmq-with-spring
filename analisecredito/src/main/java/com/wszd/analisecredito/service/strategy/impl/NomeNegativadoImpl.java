package com.wszd.analisecredito.service.strategy.impl;

import com.wszd.analisecredito.constante.MensagemConstante;
import com.wszd.analisecredito.domain.Proposta;
import com.wszd.analisecredito.exceptions.StrategyException;
import com.wszd.analisecredito.service.strategy.CalculoPonto;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1)
@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        if(nomeNegativado()){
            throw new StrategyException(MensagemConstante.CLIENTE_NEGATIVADO);
        }
        return 100;
    }

    private boolean nomeNegativado(){
        return new Random().nextBoolean();
    }
}
