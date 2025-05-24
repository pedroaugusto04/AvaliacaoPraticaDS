package com.avaliacaods.bank.services.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avaliacaods.bank.models.authentication.User;
import com.avaliacaods.bank.models.authentication.UserDetailsImpl;
import com.avaliacaods.bank.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        User user = userRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDetailsImpl(user);
    }

    public User getLoggedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            userDetails = (UserDetailsImpl) authentication.getPrincipal();
        } else return null;

        return userDetails.getUser();
    }
}