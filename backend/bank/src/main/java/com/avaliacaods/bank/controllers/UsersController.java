package com.avaliacaods.bank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaods.bank.dtos.JwtTokenDTO;
import com.avaliacaods.bank.dtos.LoginUserDTO;
import com.avaliacaods.bank.dtos.UserRequestDTO;
import com.avaliacaods.bank.dtos.UserResponseDTO;
import com.avaliacaods.bank.services.UserService;


@RestController
@RequestMapping("/users")
public class UsersController {
    
    private UserService userService;

    UsersController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")   
    public ResponseEntity<UserResponseDTO> getUserInfo() {

        UserResponseDTO userResponseDTO = this.userService.getUserInfo();

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDTO) {
        JwtTokenDTO token = userService.authenticateUser(loginUserDTO);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRequestDTO registerUserDTO) {

        this.userService.registerUser(registerUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
