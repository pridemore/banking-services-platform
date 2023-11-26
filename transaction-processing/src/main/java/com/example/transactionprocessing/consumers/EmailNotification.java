package com.example.transactionprocessing.consumers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailNotification {
    private String sender;

    private String subject;

    private String body;

    private String to;
}
