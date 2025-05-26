package com.avaliacaods.bank.dtos;

import com.avaliacaods.bank.models.enums.TipoLancamento;

public class LancamentoDTO {
    
    String numeroConta;
    String valor;
    TipoLancamento tipoLancamento;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }
}
