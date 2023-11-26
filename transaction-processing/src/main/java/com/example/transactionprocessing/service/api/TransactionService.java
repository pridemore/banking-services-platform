package com.example.transactionprocessing.service.api;

import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.dto.CheckBalanceDto;

public interface TransactionService {
    CommonResponse checkBalance(CheckBalanceDto checkBalanceDto);

    CommonResponse deposit();

    CommonResponse withdraw();

}
