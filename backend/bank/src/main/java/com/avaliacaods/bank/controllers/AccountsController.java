package com.avaliacaods.bank.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaods.bank.dtos.ContaDTO;
import com.avaliacaods.bank.services.AccountsService;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private AccountsService accountsService;

    AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("")
    public ResponseEntity<List<ContaDTO>> getUserAccounts() {
        return ResponseEntity.ok(this.accountsService.getUserAccounts());
    }

    @PostMapping("")
    public ResponseEntity<ContaDTO> createUserAccount() throws URISyntaxException {

        ContaDTO createdAccountDTO = this.accountsService.createUserAccount();

        URI location = URI.create("/accounts/" + createdAccountDTO.getId());

        return ResponseEntity.created(location).body(createdAccountDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getUserAccountById(@PathVariable Long id) {
        
        return ResponseEntity.ok(this.accountsService.getUserAccountById(id));
    }

}
