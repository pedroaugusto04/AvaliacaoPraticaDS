package com.avaliacaods.bank.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.avaliacaods.bank.dtos.LancamentoDTO;
import com.avaliacaods.bank.exceptions.InvalidTransactionException;
import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.Lancamento;
import com.avaliacaods.bank.models.enums.TipoLancamento;
import com.avaliacaods.bank.repositories.AccountsRepository;
import com.avaliacaods.bank.repositories.TransactionsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WithdrawalService {

    private TransactionsRepository transactionsRepository;
    private AccountsRepository accountsRepository;

    WithdrawalService (AccountsRepository accountsRepository, TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }
    
    public void withdraw(LancamentoDTO withdrawalDTO) {

        Conta conta = this.accountsRepository.findByNumero(withdrawalDTO.getNumeroConta()).orElseThrow(() -> new EntityNotFoundException());

        BigDecimal saldo = conta.getLancamentos().stream()
        .map(lancamento -> {
            return switch (lancamento.getTipo()) {
                case CREDITO -> lancamento.getValor();
                case DEBITO -> lancamento.getValor().negate(); // subtrai caso encontre uma operacao de saque
                default -> BigDecimal.ZERO;
            }; 
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valor = new BigDecimal(withdrawalDTO.getValor());

        if (valor.compareTo(saldo) > 0){
            throw new InvalidTransactionException("Transação inválida","O saldo atual é menor do que a tentativa de saque");
        }

        withdrawalDTO.setTipoLancamento(TipoLancamento.DEBITO);

        Lancamento withDrawal = new Lancamento(withdrawalDTO,conta);

        this.transactionsRepository.save(withDrawal);
    }
}
