package com.avaliacaods.bank.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaods.bank.models.Conta;
import com.avaliacaods.bank.services.AccountsService;


@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private AccountsService accountsService;

    AccountsController(AccountsService accountsService){
        this.accountsService = accountsService;
    }

    @GetMapping("")
    public ResponseEntity<List<Conta>> getUserAccounts() {
        return ResponseEntity.ok(this.accountsService.getUserAccounts());
    }

}
