package com.avaliacaods.bank.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.avaliacaods.bank.dtos.ContaDTO;
import com.avaliacaods.bank.dtos.LancamentoDTO;
import com.avaliacaods.bank.dtos.LimitDTO;
import com.avaliacaods.bank.dtos.TransferenceDTO;
import com.avaliacaods.bank.exceptions.InvalidTransactionException;
import com.avaliacaods.bank.models.Client;
import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.Lancamento;
import com.avaliacaods.bank.models.authentication.User;
import com.avaliacaods.bank.models.enums.TipoLancamento;
import com.avaliacaods.bank.models.enums.TipoOperacao;
import com.avaliacaods.bank.repositories.AccountsRepository;
import com.avaliacaods.bank.repositories.ClientRepository;
import com.avaliacaods.bank.repositories.TransactionsRepository;
import com.avaliacaods.bank.services.authentication.UserDetailsServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountsService {

    private AccountsRepository accountsRepository;
    private UserDetailsServiceImpl userDetailsService;
    private ClientRepository clientRepository;
    private TransactionsRepository transactionsRepository;

    AccountsService(AccountsRepository accountsRepository, UserDetailsServiceImpl userDetailsService,
            ClientRepository clientRepository, TransactionsRepository transactionsRepository) {
        this.accountsRepository = accountsRepository;
        this.userDetailsService = userDetailsService;
        this.clientRepository = clientRepository;
        this.transactionsRepository = transactionsRepository;
    }

    public ContaDTO createUserAccount() {
        User user = this.userDetailsService.getLoggedUser();

        Client client = this.clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        boolean accountNumberAlreadyExists = true;

        StringBuilder stringNumberAccount = new StringBuilder();

        while (accountNumberAlreadyExists) {
            stringNumberAccount = new StringBuilder();
            stringNumberAccount.append(client.getNome().substring(0, 2).toUpperCase()); // 2 primeiras letras do nome
            stringNumberAccount.append("-");

            int randomNumberSixDigits = ThreadLocalRandom.current().nextInt(100000, 1000000);

            stringNumberAccount.append(String.valueOf(randomNumberSixDigits));

            accountNumberAlreadyExists = this.accountsRepository.existsByNumero(stringNumberAccount.toString());
        }

        Conta account = new Conta(client, stringNumberAccount.toString());

        this.accountsRepository.save(account);

        ContaDTO createdAccountDTO = new ContaDTO(account);

        return createdAccountDTO;
    }

    public List<ContaDTO> getUserAccounts() {

        User user = this.userDetailsService.getLoggedUser();

        List<Conta> contas = this.accountsRepository.findByClienteUserId(user.getId());

        List<ContaDTO> contasDTO = new ArrayList<>(contas.size());

        contas.forEach(conta -> {
            ContaDTO contaDTO = new ContaDTO(conta);
            contasDTO.add(contaDTO);
        });

        return contasDTO;
    }

    public List<ContaDTO> getAllAccounts() {
        
        List<Conta> contas = this.accountsRepository.findAll();

        List<ContaDTO> contasDTO = new ArrayList<>(contas.size());

        contas.forEach(conta -> {
            ContaDTO contaDTO = new ContaDTO(conta);
            contasDTO.add(contaDTO);
        });

        return contasDTO;
    }

    public ContaDTO getUserAccountById(Long id) {

        User user = this.userDetailsService.getLoggedUser();

        Conta conta = this.accountsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        // somente se a conta for do usuario logado
        if (!conta.getCliente().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("");
        }

        return new ContaDTO(conta);
    }

    public void updateLimit(LimitDTO limitDTO) {

        Conta conta = this.accountsRepository.findByNumero(limitDTO.getNumeroConta())
                .orElseThrow(() -> new EntityNotFoundException());

        conta.setLimiteCredito(Double.valueOf(limitDTO.getValor()));

        this.accountsRepository.save(conta);
    }


    public void transference(TransferenceDTO transferenceDTO) {

        Conta contaOrigem = this.accountsRepository.findByNumero(transferenceDTO.getNumeroContaOrigem()).orElseThrow(() -> new EntityNotFoundException());

        BigDecimal saldo = contaOrigem.getLancamentos().stream()
        .map(lancamento -> {
            return switch (lancamento.getTipo()) {
                case CREDITO -> lancamento.getValor();
                case DEBITO -> lancamento.getValor().negate(); // subtrai caso encontre uma operacao de saque
                default -> BigDecimal.ZERO;
            }; 
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        // soma o saldo com o limite de credito da conta
        saldo = saldo.add(new BigDecimal(contaOrigem.getLimiteCredito()));

        if (new BigDecimal(transferenceDTO.getValor()).compareTo(saldo) > 0){
            throw new InvalidTransactionException("Transação inválida","O saldo atual somado com o limite de cŕedito é menor do que a tentativa de transferência");
        }

        Conta contaDestino = this.accountsRepository.findByNumero(transferenceDTO.getNumeroContaDestino()).orElseThrow(() -> new EntityNotFoundException());

        if (!contaOrigem.getCliente().getId().equals(contaDestino.getCliente().getId())) {
            // se a conta for de clientes diferentes, taxa de 10% sobre o valor transferido na conta de origem
            taxarConta(contaOrigem,Double.valueOf(transferenceDTO.getValor()));
        }
        
        // retira dinheiro da conta de origem (debito)
        retiraTransferencia(contaOrigem, transferenceDTO.getValor());

        // incrementa dinheiro na conta de destino (credito)
        incrementaTransferencia(contaDestino, transferenceDTO.getValor());
    }

    public void retiraTransferencia(Conta contaOrigem, String valorTransferido) {

        LancamentoDTO lancamentoDTO = new LancamentoDTO(contaOrigem.getNumero(),valorTransferido, TipoLancamento.DEBITO,TipoOperacao.TRANSFERENCIA);

        Lancamento lancamento = new Lancamento(lancamentoDTO,contaOrigem);

        this.transactionsRepository.save(lancamento);
    }

    public void incrementaTransferencia(Conta contaDestino, String valorTransferido) {
        LancamentoDTO lancamentoDTO = new LancamentoDTO(contaDestino.getNumero(),valorTransferido, TipoLancamento.CREDITO,TipoOperacao.TRANSFERENCIA);

        Lancamento lancamento = new Lancamento(lancamentoDTO,contaDestino);

        this.transactionsRepository.save(lancamento);
    }

    public void taxarConta(Conta contaOrigem, Double valorTransferido) {
        Double valor = (double)10/(double)100 * valorTransferido;

        LancamentoDTO lancamentoDTO = new LancamentoDTO(contaOrigem.getNumero(),String.valueOf(valor), TipoLancamento.DEBITO,TipoOperacao.TAXA);

        Lancamento lancamento = new Lancamento(lancamentoDTO,contaOrigem);

        this.transactionsRepository.save(lancamento);
    }
}
