package com.example.accountmanagement.controller;

import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.dto.AccountDto;
import com.example.accountmanagement.domain.dto.UpdateAccountDto;
import com.example.accountmanagement.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public CommonResponse createAccount(@RequestBody AccountDto accountDto){
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/getUserDetailsByAccount/{accountNumber}")
    public CommonResponse getUserDetailsByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return accountService.getUserDetailByAccount(accountNumber);
    }

    @PutMapping("/update")
    public CommonResponse updateAccount(@RequestBody UpdateAccountDto updateAccountDto){
        return accountService.updateAccount(updateAccountDto);
    }


}
