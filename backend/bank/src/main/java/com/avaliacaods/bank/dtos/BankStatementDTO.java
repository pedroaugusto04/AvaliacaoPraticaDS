package com.avaliacaods.bank.dtos;

import java.time.format.DateTimeFormatter;

import com.avaliacaods.bank.models.Lancamento;
import com.avaliacaods.bank.models.enums.TipoLancamento;

public class BankStatementDTO {
    private String Data;
    private String valor;
    private TipoLancamento tipo;

    public BankStatementDTO() {}

    public BankStatementDTO(Lancamento lancamento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.Data = lancamento.getData().format(formatter);
        this.valor = lancamento.getValor().toString();
        this.tipo = lancamento.getTipo();
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
    

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

}
