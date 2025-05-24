package com.avaliacaods.bank.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaods.bank.dtos.LancamentoDTO;
import com.avaliacaods.bank.services.WithdrawalService;


@RestController
@RequestMapping("/withdrawals")
public class WithdrawalController {

    private WithdrawalService withdrawalService;

    WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping("")
    public ResponseEntity<Void> withdraw(@RequestBody LancamentoDTO withdrawDTO) {

        this.withdrawalService.withdraw(withdrawDTO);

        return ResponseEntity.ok().build();
    }

}
