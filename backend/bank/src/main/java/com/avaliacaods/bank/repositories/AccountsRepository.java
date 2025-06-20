package com.avaliacaods.bank.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaliacaods.bank.models.Conta;

@Repository
public interface AccountsRepository extends JpaRepository<Conta,Long>{
    List<Conta> findByClienteUserId(Long userId);

    List<Conta> findByClienteId(Long clienteId);

    boolean existsByNumero(String numero);

    Optional<Conta> findByNumero(String numero);
}
