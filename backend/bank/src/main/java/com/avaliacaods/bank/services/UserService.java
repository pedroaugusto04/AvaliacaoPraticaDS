package com.avaliacaods.bank.services;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.avaliacaods.bank.configurations.SecurityConfiguration;
import com.avaliacaods.bank.dtos.JwtTokenDTO;
import com.avaliacaods.bank.dtos.LoginUserDTO;
import com.avaliacaods.bank.dtos.UserRequestDTO;
import com.avaliacaods.bank.dtos.UserResponseDTO;
import com.avaliacaods.bank.exceptions.InvalidCpfException;
import com.avaliacaods.bank.models.Client;
import com.avaliacaods.bank.models.authentication.Role;
import com.avaliacaods.bank.models.authentication.User;
import com.avaliacaods.bank.models.authentication.UserDetailsImpl;
import com.avaliacaods.bank.models.enums.authentication.RoleName;
import com.avaliacaods.bank.repositories.ClientRepository;
import com.avaliacaods.bank.repositories.UserRepository;
import com.avaliacaods.bank.services.authentication.JwtTokenService;
import com.avaliacaods.bank.services.authentication.UserDetailsServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private UserDetailsServiceImpl userDetailsService;

    UserService(ClientRepository clientRepository, SecurityConfiguration securityConfiguration,AuthenticationManager authenticationManager,
    JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.securityConfiguration = securityConfiguration;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public UserResponseDTO getUserInfo() {
        User user = this.userDetailsService.getLoggedUser();

        Client client = this.clientRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException());
        
        UserResponseDTO userResponseDTO = new UserResponseDTO(client.getNome());

        return userResponseDTO;
    }

    public JwtTokenDTO authenticateUser(LoginUserDTO loginUserDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.getCpf(), loginUserDTO.getSenha());

        // lanca BadCredentialsException em caso de falha
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken); 

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    public void registerUser(UserRequestDTO registerUserDTO) {
        // verificacao de CPF
        boolean cpfAlreadyExists = this.userRepository.existsByCpf(registerUserDTO.getCpf());

        if (cpfAlreadyExists){
            throw new InvalidCpfException("CPF inválido","Já existe um cliente registrado com este CPF");
        }
    
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);

        Client newClient = new Client(registerUserDTO,
                securityConfiguration.passwordEncoder().encode(registerUserDTO.getSenha()),
                Set.of(userRole));

        this.clientRepository.save(newClient);
    }
}
