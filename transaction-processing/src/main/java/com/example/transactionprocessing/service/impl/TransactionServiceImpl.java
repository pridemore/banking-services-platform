package com.example.transactionprocessing.service.impl;

import com.example.transactionprocessing.common.SystemConstants;
import com.example.transactionprocessing.common.Utils;
import com.example.transactionprocessing.common.enums.TransactionStatus;
import com.example.transactionprocessing.common.enums.TransactionType;
import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.Transaction;
import com.example.transactionprocessing.domain.dto.DepositDto;
import com.example.transactionprocessing.domain.dto.StatementDto;
import com.example.transactionprocessing.domain.dto.UpdateAccountDto;
import com.example.transactionprocessing.domain.dto.WithdrawDto;
import com.example.transactionprocessing.feignclients.AccountManagementConsumer;
import com.example.transactionprocessing.persistance.TransactionRepository;
import com.example.transactionprocessing.service.api.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final AccountManagementConsumer accountManagementConsumer;

    private final TransactionRepository transactionRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${transaction.notification.service.exchange}")
    private String exchange;
    @Value("${transaction.notification.service.key}")
    private String routingKey;

    @Value("${api.key}")
    private String apikey;


    @Override
    public CommonResponse checkBalance(String accountNumber) {
        CommonResponse balanceByAccount = accountManagementConsumer.getBalanceByAccount(apikey,accountNumber);
        log.info("Balance Returned : {}", balanceByAccount.getResult());
        return balanceByAccount;
    }

    @Override
    public CommonResponse deposit(DepositDto depositDto) {
        CommonResponse getBalanceByAccount = accountManagementConsumer.getBalanceByAccount(apikey,depositDto.getAccountNumber());
        Double existingBalance = (Double) getBalanceByAccount.getResult();
        log.info("Existing Balance : {}", existingBalance);
        Transaction transactionResponse = processTransactionDepositRequest(depositDto, existingBalance);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, transactionResponse);
    }

    @Override
    public CommonResponse withdraw(WithdrawDto withdrawDto) {
        CommonResponse getBalanceByAccount = accountManagementConsumer.getBalanceByAccount(apikey,withdrawDto.getAccountNumber());
        Double existingBalance = (Double) getBalanceByAccount.getResult();
        log.info("Existing Balance : {}", existingBalance);
        Transaction transactionResponse = processTransactionWithdrawRequest(withdrawDto, existingBalance);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, transactionResponse);
    }

    @Override
    public CommonResponse getTransactionHistory(StatementDto statementDto) {
        List<Transaction> transactionHistory = transactionRepository.findTransactionHistory(
                statementDto.getAccountNumber(), statementDto.getStartDate(), statementDto.getEndDate());
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, transactionHistory);
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
                .status(TransactionStatus.PENDING)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
        Transaction savedDepositTransaction = transactionRepository.save(transaction);
        processTransactionAndUpdateBalance(savedDepositTransaction);
        return savedDepositTransaction;
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
                .status(TransactionStatus.PENDING)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
        Transaction savedWithdrawTransaction = transactionRepository.save(transaction);
        processTransactionAndUpdateBalance(savedWithdrawTransaction);
        return savedWithdrawTransaction;
    }

    @Async
    void processTransactionAndUpdateBalance(Transaction transaction) {
        rabbitTemplate.convertAndSend(exchange, routingKey, transaction.getId());
        transaction.setStatus(TransactionStatus.ADDED_TO_QUEUE);
        transactionRepository.save(transaction);
    }


}
