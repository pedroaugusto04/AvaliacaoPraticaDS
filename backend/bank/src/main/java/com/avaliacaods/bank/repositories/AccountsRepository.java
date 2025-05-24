package com.avaliacaods.bank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaliacaods.bank.models.Conta;

@Repository
public interface AccountsRepository extends JpaRepository<Conta,Integer>{
    List<Conta> findByClienteUserId(Long userId);
}
