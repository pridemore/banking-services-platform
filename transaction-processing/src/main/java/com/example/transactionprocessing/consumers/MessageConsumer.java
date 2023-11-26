package com.example.transactionprocessing.consumers;

import com.example.transactionprocessing.common.enums.TransactionStatus;
import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.Transaction;
import com.example.transactionprocessing.domain.dto.UpdateAccountDto;
import com.example.transactionprocessing.feignclients.AccountManagementConsumer;
import com.example.transactionprocessing.persistance.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final AccountManagementConsumer accountManagementConsumer;

    private final TransactionRepository transactionRepository;

    private final EmailService emailService;

    private final MessagePropertiesService messagePropertiesService;

    @Value("${spring.mail.username}")
    private String username;

    private static final String QUEUE = "transaction-notification-service-queue";

    @RabbitListener(queues = QUEUE, concurrency = "1")
    public void listenAndFetchResponse(Long transactionId) {
        log.info("### In Consumer service : {}", transactionId);
        Optional<Transaction> transactionById = transactionRepository.findById(transactionId);
        if (transactionById.isPresent()) {
            log.info("transactionById : {}", transactionById.get());
            UpdateAccountDto updateBalance = UpdateAccountDto.builder()
                    .accountNumber(transactionById.get().getAccountNumber())
                    .accountBalance(transactionById.get().getBalance())
                    .build();
            accountManagementConsumer.updateAccount(updateBalance);
            transactionById.get().setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(transactionById.get());
            String accountEmail = "2020dataanalytics@gmail.com";
            sendEmailNotification(transactionById.get(), accountEmail);
            log.info("Updated Balance : {}", updateBalance.getAccountBalance());
        }


    }

    private void sendEmailNotification(Transaction transaction, String accountEmail) {

        EmailNotification buildEmail = EmailNotification.builder()
                .sender(username)
                .subject("Banking Platform Notification")
                .body(String.format(messagePropertiesService.getByKey("messages.email.transaction.processing.complete"),
                        transaction.getAccountNumber(), transaction.getTransactionType(), transaction.getAmount(),
                        transaction.getTransactionReference(),
                        transaction.getBalance()))
                .to(accountEmail)
                .build();

        emailService.send(buildEmail);
    }
}
