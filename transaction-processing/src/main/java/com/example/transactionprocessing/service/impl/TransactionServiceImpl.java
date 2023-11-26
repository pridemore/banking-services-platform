package com.example.transactionprocessing.service.impl;

import com.example.transactionprocessing.common.SystemConstants;
import com.example.transactionprocessing.common.enums.TransactionStatus;
import com.example.transactionprocessing.common.enums.TransactionType;
import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.Transaction;
import com.example.transactionprocessing.domain.dto.CheckBalanceDto;
import com.example.transactionprocessing.domain.dto.DepositDto;
import com.example.transactionprocessing.domain.dto.UpdateAccountDto;
import com.example.transactionprocessing.domain.dto.WithdrawDto;
import com.example.transactionprocessing.feignclients.AccountManagementConsumer;
import com.example.transactionprocessing.persistance.TransactionRepository;
import com.example.transactionprocessing.service.api.TransactionService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.OffsetDateTime;
import java.util.UUID;

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
        Transaction transactionResponse = processDeposit(depositDto, existingBalance);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS,transactionResponse);
    }

    @Override
    public CommonResponse withdraw(WithdrawDto withdrawDto) {
        return null;
    }


    private Transaction processDeposit(DepositDto depositDto, Double existingBalance) {
        String reference = generateTransactionReference();
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
        Transaction processedTransaction = transactionRepository.save(transaction);

        log.info("Transaction Reference : {}",processedTransaction.getTransactionReference());

        UpdateAccountDto updateBalance= UpdateAccountDto.builder()
                .accountNumber(depositDto.getAccountNumber())
                .accountBalance(balance)
                .build();

        accountManagementConsumer.updateAccount(updateBalance);

        log.info("Updated Balance : {}",updateBalance.getAccountBalance());

        return processedTransaction;
    }

    private String generateTransactionReference() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        return "TRX" + uniqueId;
    }

}
