package com.avaliacaods.bank.dtos;

import com.avaliacaods.bank.models.Conta;

public class ContaDTO {
    private Long id;
    private String numero;
    private String cpf;


    public ContaDTO() {}

    public ContaDTO(Conta conta){
        this.id = conta.getId();
        this.numero = conta.getNumero();
        this.cpf = conta.getCliente().getUser().getCpf();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
