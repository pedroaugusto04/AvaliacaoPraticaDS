package com.avaliacaods.bank.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaods.bank.dtos.BankStatementDTO;
import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.Lancamento;
import com.avaliacaods.bank.repositories.AccountsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BankStatementsService {

    private AccountsRepository accountsRepository;

    BankStatementsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }
    
    public List<BankStatementDTO> getAccountBankStatements(String numeroConta) {
        
        Conta conta = this.accountsRepository.findByNumero(numeroConta).orElseThrow(() -> new EntityNotFoundException());

        List<BankStatementDTO> bankStatements = new ArrayList<>(conta.getLancamentos().size());

        conta.getLancamentos().forEach(lancamento -> {
            BankStatementDTO bankStatementDTO = new BankStatementDTO(lancamento);
            bankStatements.add(bankStatementDTO);
        });

        return bankStatements;
    }


    public BigDecimal getSaldoAllClientAccounts(Long clienteId) {
        
        List<Conta> accounts = this.accountsRepository.findByClienteId(clienteId);

        BigDecimal sum = new BigDecimal(0);

        for (Conta conta : accounts) {
            for (Lancamento lancamento : conta.getLancamentos()) {
                sum = sum.add(lancamento.getValor());
            }
        }

        return sum;
    }
}
