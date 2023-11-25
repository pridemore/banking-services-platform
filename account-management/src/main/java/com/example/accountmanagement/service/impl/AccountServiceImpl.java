package com.example.accountmanagement.service.impl;

import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.persistance.AccountRepository;
import com.example.accountmanagement.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
