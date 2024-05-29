package com.wszd.analisecredito.service.strategy.impl;

import com.wszd.analisecredito.domain.Proposta;
import com.wszd.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class RendaMaiorValorSolicitado implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) ? 100 : 0;
    }
    
    private boolean rendaMaiorValorSolicitado(Proposta proposta){
        return proposta.getUsuario().getRenda() > proposta.getValorSolicitado();

    }
}
