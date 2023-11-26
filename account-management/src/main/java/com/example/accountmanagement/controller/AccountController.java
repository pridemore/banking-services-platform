package com.example.accountmanagement.controller;

import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.dto.AccountDto;
import com.example.accountmanagement.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public CommonResponse createAccount(@RequestBody AccountDto accountDto){
        return accountService.createAccount(accountDto);
    }
}
