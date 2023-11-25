package com.example.accountmanagement.persistance;

import com.example.accountmanagement.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
