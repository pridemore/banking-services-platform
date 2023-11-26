package com.example.transactionprocessing.domain.dto;

import com.example.transactionprocessing.common.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositDto {

    private String accountNumber;

    private double amount;

}
