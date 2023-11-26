package com.example.transactionprocessing.service.impl;

import com.example.transactionprocessing.common.SystemConstants;
import com.example.transactionprocessing.common.Utils;
import com.example.transactionprocessing.common.enums.TransactionStatus;
import com.example.transactionprocessing.common.enums.TransactionType;
import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.Transaction;
import com.example.transactionprocessing.domain.dto.DepositDto;
import com.example.transactionprocessing.domain.dto.UpdateAccountDto;
import com.example.transactionprocessing.domain.dto.WithdrawDto;
import com.example.transactionprocessing.feignclients.AccountManagementConsumer;
import com.example.transactionprocessing.persistance.TransactionRepository;
import com.example.transactionprocessing.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {


    private final AccountManagementConsumer accountManagementConsumer;

    private final TransactionRepository transactionRepository;


    @Override
    public CommonResponse checkBalance(String accountNumber) {
        CommonResponse balanceByAccount = accountManagementConsumer.getBalanceByAccount(accountNumber);
        log.info("Balance Returned : {}", balanceByAccount.getResult());
        return balanceByAccount;
    }

    @Override
    public CommonResponse deposit(DepositDto depositDto) {
        CommonResponse getBalanceByAccount = accountManagementConsumer.getBalanceByAccount(depositDto.getAccountNumber());
        Double existingBalance = (Double) getBalanceByAccount.getResult();
        log.info("Existing Balance : {}", existingBalance);
        Transaction transactionResponse = processTransactionDepositRequest(depositDto, existingBalance);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, transactionResponse);
    }

    @Override
    public CommonResponse withdraw(WithdrawDto withdrawDto) {
        CommonResponse getBalanceByAccount = accountManagementConsumer.getBalanceByAccount(withdrawDto.getAccountNumber());
        Double existingBalance = (Double) getBalanceByAccount.getResult();
        log.info("Existing Balance : {}", existingBalance);
        Transaction transactionResponse = processTransactionWithdrawRequest(withdrawDto, existingBalance);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, transactionResponse);
    }

    private Transaction processTransactionDepositRequest(DepositDto depositDto, Double existingBalance) {
        log.info("### In Deposit Transaction");
        String reference = Utils.generateTransactionReference();
        double balance = existingBalance + depositDto.getAmount();
        Transaction transaction = Transaction.builder()
                .transactionReference(reference)
                .accountNumber(depositDto.getAccountNumber())
                .amount(depositDto.getAmount())
                .balance(balance)
                .transactionType(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();

        return saveTransactionAndUpdateBalance(transaction);
    }

    private Transaction processTransactionWithdrawRequest(WithdrawDto withdrawDto, Double existingBalance) {
        log.info("### In Withdraw Transaction");
        String reference = Utils.generateTransactionReference();
        double balance = existingBalance - withdrawDto.getAmount();
        Transaction transaction = Transaction.builder()
                .transactionReference(reference)
                .accountNumber(withdrawDto.getAccountNumber())
                .amount(withdrawDto.getAmount())
                .balance(balance)
                .transactionType(TransactionType.WITHDRAWAL)
                .status(TransactionStatus.COMPLETED)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
        return saveTransactionAndUpdateBalance(transaction);
    }

    private Transaction saveTransactionAndUpdateBalance(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);

        log.info("Transaction Reference : {}", savedTransaction.getTransactionReference());

        UpdateAccountDto updateBalance = UpdateAccountDto.builder()
                .accountNumber(transaction.getAccountNumber())
                .accountBalance(transaction.getBalance())
                .build();

        accountManagementConsumer.updateAccount(updateBalance);

        log.info("Updated Balance : {}", updateBalance.getAccountBalance());
        return savedTransaction;
    }
}
