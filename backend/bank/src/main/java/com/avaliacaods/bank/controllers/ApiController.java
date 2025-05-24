package com.avaliacaods.bank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiController {
    

    @GetMapping("/ping")
    public ResponseEntity<Void> ping() {
        System.out.println("API online");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
