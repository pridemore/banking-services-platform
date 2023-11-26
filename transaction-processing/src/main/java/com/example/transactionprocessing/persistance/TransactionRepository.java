package com.example.transactionprocessing.persistance;

import com.example.transactionprocessing.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "select * from account_management_db.transaction where accountNumber=:accountNumber and  dateCreated between :startDate and :endDate",nativeQuery = true)
    List<Transaction> findTransactionHistory(String accountNumber, LocalDate startDate,LocalDate endDate);
}
