package com.avaliacaods.bank.dtos;

import com.avaliacaods.bank.models.enums.TipoLancamento;
import com.avaliacaods.bank.models.enums.TipoOperacao;

public class LancamentoDTO {
    
    String numeroConta;
    String valor;
    TipoLancamento tipoLancamento;
    TipoOperacao tipoOperacao;

    public LancamentoDTO() {}

    public LancamentoDTO(String numeroConta, String valor, TipoLancamento tipoLancamento, TipoOperacao tipoOperacao) {
        this.numeroConta = numeroConta;
        this.valor = valor;
        this.tipoLancamento = tipoLancamento;
        this.tipoOperacao = tipoOperacao;
    }

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

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }
}
