package com.example.accountmanagement.service.impl;

import com.example.accountmanagement.common.SystemConstants;
import com.example.accountmanagement.common.enums.AccountType;
import com.example.accountmanagement.common.enums.Status;
import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.domain.dto.AccountDto;
import com.example.accountmanagement.persistance.AccountRepository;
import com.example.accountmanagement.persistance.UserDetailRepository;
import com.example.accountmanagement.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserDetailRepository userDetailRepository;

    private final AccountRepository accountRepository;

    @Override
    public CommonResponse createAccount(AccountDto accountDto) {

        Optional<UserDetail> foundUserDetail = userDetailRepository.findByNationalId(accountDto.getNationalId());

        if (!foundUserDetail.isPresent()) {
            return new CommonResponse().buildErrorResponse("Could not create Account. User does not exist");
        }

        Account savedAccount = accountRepository.save(buildAccount(accountDto, foundUserDetail.get()));

        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, savedAccount);
    }

    private Account buildAccount(AccountDto accountDto, UserDetail userDetail) {
        return Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .accountType(accountDto.getAccountType())
                .userDetail(userDetail)
                .accountBalance(0.0)
                .status(Status.ACTIVE)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
    }
}
