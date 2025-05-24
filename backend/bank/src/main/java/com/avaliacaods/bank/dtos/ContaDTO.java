package com.avaliacaods.bank.dtos;

import java.math.BigDecimal;

import com.avaliacaods.bank.models.Conta;

public class ContaDTO {
    
    private Long id;
    private String numero;
    private BigDecimal saldo;


    public ContaDTO() {}

    public ContaDTO(Conta conta){
        this.id = conta.getId();
        this.numero = conta.getNumero();
        
        // soma o saldo de todos os lancamentos pra ver o estado atual da conta
        this.saldo = conta.getLancamentos().stream().map((lancamento) -> lancamento.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
