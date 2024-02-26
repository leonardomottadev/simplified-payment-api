package com.leomottadev.desafiopicpaysimplificado.repositories;

import com.leomottadev.desafiopicpaysimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
