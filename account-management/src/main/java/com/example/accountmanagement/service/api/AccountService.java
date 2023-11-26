package com.example.accountmanagement.service.api;

import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.domain.dto.AccountDto;

public interface AccountService {

    CommonResponse createAccount(AccountDto accountDto);
}
