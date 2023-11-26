package com.example.transactionprocessing.controller;

import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.dto.DepositDto;
import com.example.transactionprocessing.domain.dto.StatementDto;
import com.example.transactionprocessing.domain.dto.WithdrawDto;
import com.example.transactionprocessing.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/balance/{accountNumber}")
    public CommonResponse getAccountBalance(@PathVariable("accountNumber") String accountNumber) {
        return transactionService.checkBalance(accountNumber);
    }

    @PostMapping("/deposit")
    public CommonResponse depositFunds(@RequestBody DepositDto depositDto) {
        return transactionService.deposit(depositDto);
    }

    @PostMapping("/withdraw")
    public CommonResponse WithdrawFunds(@RequestBody WithdrawDto withdrawDto) {
        return transactionService.withdraw(withdrawDto);
    }

    @PostMapping("/statement")
    public CommonResponse getTransactionHistory(@RequestBody StatementDto statementDto){
        return transactionService.getTransactionHistory(statementDto);
    }

}
