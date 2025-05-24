package com.avaliacaods.bank.services;

import org.springframework.stereotype.Service;

import com.avaliacaods.bank.dtos.LancamentoDTO;
import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.Lancamento;
import com.avaliacaods.bank.models.enums.TipoLancamento;
import com.avaliacaods.bank.repositories.AccountsRepository;
import com.avaliacaods.bank.repositories.TransactionsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepositsService {

    private TransactionsRepository transactionsRepository;
    private AccountsRepository accountsRepository;

    DepositsService(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }
    
    public void deposit(LancamentoDTO depositDTO) {

        Conta conta = this.accountsRepository.findByNumero(depositDTO.getNumeroConta()).orElseThrow(() -> new EntityNotFoundException());

        depositDTO.setTipoLancamento(TipoLancamento.CREDITO);

        Lancamento deposit = new Lancamento(depositDTO,conta);

        this.transactionsRepository.save(deposit);
    }
}
