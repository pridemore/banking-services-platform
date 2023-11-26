package com.example.accountmanagement.domain.dto;

import com.example.accountmanagement.common.enums.AccountType;
import com.example.accountmanagement.domain.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String accountNumber;

    private AccountType accountType;

    private String  nationalId;

}
