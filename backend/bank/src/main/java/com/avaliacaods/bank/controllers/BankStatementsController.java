package com.avaliacaods.bank.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaods.bank.dtos.BankStatementDTO;
import com.avaliacaods.bank.services.BankStatementsService;

@RestController
@RequestMapping("/bankstatements")
public class BankStatementsController {

    private BankStatementsService bankStatementsService;

    BankStatementsController(BankStatementsService bankStatementsService) {
        this.bankStatementsService = bankStatementsService;
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<BankStatementDTO>> getAccountBankStatements(@PathVariable String numeroConta) {
        return ResponseEntity.ok(this.bankStatementsService.getAccountBankStatements(numeroConta));
    }
}
