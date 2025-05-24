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
public class WithdrawalService {

    private TransactionsRepository transactionsRepository;
    private AccountsRepository accountsRepository;

    WithdrawalService (AccountsRepository accountsRepository, TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }
    
    public void withdraw(LancamentoDTO withdrawalDTO) {

        Conta conta = this.accountsRepository.findByNumero(withdrawalDTO.getNumeroConta()).orElseThrow(() -> new EntityNotFoundException());

        withdrawalDTO.setTipoLancamento(TipoLancamento.DEBITO);

        Lancamento withDrawal = new Lancamento(withdrawalDTO,conta);

        this.transactionsRepository.save(withDrawal);
    }
}
