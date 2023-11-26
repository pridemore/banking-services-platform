package com.example.customersupport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="ticket")
public class Ticket {

    @Id
    private String id;

    private String ticketReference;

    private String accountNumber;

    private String queryDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdated;
}
