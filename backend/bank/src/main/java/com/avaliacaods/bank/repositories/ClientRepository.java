package com.avaliacaods.bank.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaliacaods.bank.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{
    Optional<Client> findByUserId(Long userId);
}
