package com.avaliacaods.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaliacaods.bank.models.Lancamento;

@Repository
public interface TransactionsRepository extends JpaRepository<Lancamento,Long>{
    
}
