package com.example.transactionprocessing.persistance;

import com.example.transactionprocessing.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
