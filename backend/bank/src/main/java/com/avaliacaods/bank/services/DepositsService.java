package com.avaliacaods.bank.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.avaliacaods.bank.dtos.LancamentoDTO;
import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.Lancamento;
import com.avaliacaods.bank.models.enums.TipoLancamento;
import com.avaliacaods.bank.models.enums.TipoOperacao;
import com.avaliacaods.bank.repositories.AccountsRepository;
import com.avaliacaods.bank.repositories.TransactionsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepositsService {

    private TransactionsRepository transactionsRepository;
    private AccountsRepository accountsRepository;
    private BankStatementsService bankStatementService;

    DepositsService(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository,
    BankStatementsService  bankStatementService) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
        this.bankStatementService = bankStatementService;
    }

    public void deposit(LancamentoDTO depositDTO) {

        Conta account = this.accountsRepository.findByNumero(depositDTO.getNumeroConta())
        .orElseThrow(() -> new EntityNotFoundException());

        depositDTO.setTipoLancamento(TipoLancamento.CREDITO);

        // se o valor do deposito for maior que o saldo de todas as contas, credita
        // bonus de 10% sobre o valor depositado
        verifyDepositBonus(depositDTO,account);

        depositDTO.setTipoOperacao(TipoOperacao.DEPOSITO);

        Lancamento deposit = new Lancamento(depositDTO, account);

        this.transactionsRepository.save(deposit);
    }

    public void verifyDepositBonus(LancamentoDTO depositDTO, Conta account) {

        BigDecimal saldoAllAccounts = this.bankStatementService.getSaldoAllAccounts();

        if (saldoAllAccounts.compareTo(new BigDecimal(depositDTO.getValor())) < 0) {

            Double bonus = (double)10/100 * Double.parseDouble(depositDTO.getValor());

            LancamentoDTO lancamentoDTO = new LancamentoDTO(depositDTO.getNumeroConta(),String.valueOf(bonus),
            depositDTO.getTipoLancamento(), TipoOperacao.BONUS);

            Lancamento bonusDeposit = new Lancamento(lancamentoDTO,account);


            this.transactionsRepository.save(bonusDeposit);
        }
    }
}
