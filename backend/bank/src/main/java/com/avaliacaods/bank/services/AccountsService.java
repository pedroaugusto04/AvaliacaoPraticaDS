package com.avaliacaods.bank.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.avaliacaods.bank.dtos.ContaDTO;
import com.avaliacaods.bank.models.Client;
import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.authentication.User;
import com.avaliacaods.bank.repositories.AccountsRepository;
import com.avaliacaods.bank.repositories.ClientRepository;
import com.avaliacaods.bank.services.authentication.UserDetailsServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountsService {

    private AccountsRepository accountsRepository;
    private UserDetailsServiceImpl userDetailsService;
    private ClientRepository clientRepository;

    AccountsService(AccountsRepository accountsRepository, UserDetailsServiceImpl userDetailsService,
            ClientRepository clientRepository) {
        this.accountsRepository = accountsRepository;
        this.userDetailsService = userDetailsService;
        this.clientRepository = clientRepository;
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


    public ContaDTO getUserAccountById(Long id) {

        User user = this.userDetailsService.getLoggedUser();

        Conta conta = this.accountsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        // somente se a conta for do usuario logado
        if (!conta.getCliente().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("");
        }

        return new ContaDTO(conta);
    }
}
