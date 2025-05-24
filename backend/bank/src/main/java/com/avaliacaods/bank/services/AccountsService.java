package com.avaliacaods.bank.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.models.authentication.User;
import com.avaliacaods.bank.repositories.AccountsRepository;
import com.avaliacaods.bank.services.authentication.UserDetailsServiceImpl;

@Service
public class AccountsService {

    private AccountsRepository accountsRepository;
    private UserDetailsServiceImpl userDetailsService;

    AccountsService(AccountsRepository accountsRepository,UserDetailsServiceImpl userDetailsService ) {
        this.accountsRepository = accountsRepository;
        this.userDetailsService = userDetailsService;
    }

    public List<Conta> getUserAccounts() {

        User user = this.userDetailsService.getLoggedUser();
        
        return this.accountsRepository.findByClienteUserId(user.getId());
    }
}
