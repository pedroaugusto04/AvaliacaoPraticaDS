package com.avaliacaods.bank.exceptions;

public class InvalidCpfException extends RuntimeException {

    private String subMessage;

    public InvalidCpfException() {
        super("CPF inválido");
    }

    public InvalidCpfException(String message) {
        super(message);
    }

    public InvalidCpfException(String message, String subMessage) {
        super(message);
        this.subMessage = subMessage;
    }

    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }
}