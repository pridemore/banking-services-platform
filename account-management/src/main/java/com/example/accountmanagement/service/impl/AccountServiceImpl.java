package com.example.accountmanagement.service.impl;

import com.example.accountmanagement.common.SystemConstants;
import com.example.accountmanagement.common.enums.Status;
import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.domain.dto.AccountDto;
import com.example.accountmanagement.domain.dto.UpdateAccountDto;
import com.example.accountmanagement.domain.dto.UserDetailDto;
import com.example.accountmanagement.persistance.AccountRepository;
import com.example.accountmanagement.persistance.UserDetailRepository;
import com.example.accountmanagement.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public CommonResponse getUserDetailByAccount(String accountNumber) {

        Optional<Account> foundAccount = accountRepository.findByAccountNumber(accountNumber);

        if (!foundAccount.isPresent()) {
            return new CommonResponse().buildErrorResponse("Account Does Not Exist");
        }
        UserDetailDto userDetail = UserDetailDto.builder()
                .name(foundAccount.get().getUserDetail().getName())
                .surname(foundAccount.get().getUserDetail().getSurname())
                .nationalId(foundAccount.get().getUserDetail().getNationalId())
                .gender(foundAccount.get().getUserDetail().getGender())
                .dob(foundAccount.get().getUserDetail().getDob())
                .address(foundAccount.get().getUserDetail().getAddress())
                .phoneNumber(foundAccount.get().getUserDetail().getPhoneNumber())
                .email(foundAccount.get().getUserDetail().getEmail())
                .build();

        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, userDetail);
    }

    @Override
    public CommonResponse updateAccount(UpdateAccountDto updateAccountDto) {
        Optional<Account> accountToUpdate=accountRepository.findByAccountNumber(updateAccountDto.getAccountNumber());
        if(!accountToUpdate.isPresent()){
            return new CommonResponse().buildErrorResponse("Account Does Not Exist");
        }
        accountToUpdate.get().setAccountBalance(updateAccountDto.getAccountBalance());
        accountRepository.save(accountToUpdate.get());
        updateAccountDto.setEmail(accountToUpdate.get().getUserDetail().getEmail());

        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS,updateAccountDto);
    }


    @Override
    public CommonResponse getBalanceByAccount(String accountNumber) {
        Optional<Account> foundAccount = accountRepository.findByAccountNumber(accountNumber);

        if (!foundAccount.isPresent()) {
            return new CommonResponse().buildErrorResponse("Account Does Not Exist");
        }
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS,foundAccount.get().getAccountBalance());
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
