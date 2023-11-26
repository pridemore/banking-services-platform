package com.example.transactionprocessing.service.api;

import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.dto.DepositDto;
import com.example.transactionprocessing.domain.dto.WithdrawDto;

public interface TransactionService {
    CommonResponse checkBalance(String accountNumber);

    CommonResponse deposit(DepositDto depositDto);

    CommonResponse withdraw(WithdrawDto withdrawDto);

}
