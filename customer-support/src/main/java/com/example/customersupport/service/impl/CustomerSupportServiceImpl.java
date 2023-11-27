package com.example.customersupport.service.impl;

import com.example.customersupport.common.SystemConstants;
import com.example.customersupport.common.Utils;
import com.example.customersupport.common.enums.TicketStatus;
import com.example.customersupport.common.response.CommonResponse;
import com.example.customersupport.consumer.MessagePropertiesService;
import com.example.customersupport.domain.Ticket;
import com.example.customersupport.domain.dto.TicketDto;
import com.example.customersupport.domain.dto.UserDetailDto;
import com.example.customersupport.feignclients.AccountManagementConsumer;
import com.example.customersupport.notification.EmailNotification;
import com.example.customersupport.notification.EmailService;
import com.example.customersupport.persistance.TicketRepository;
import com.example.customersupport.service.api.CustomerSupportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerSupportServiceImpl implements CustomerSupportService {
    private final TicketRepository ticketRepository;

    private final AccountManagementConsumer accountManagementConsumer;

    private final ObjectMapper objectMapper;

    private final EmailService emailService;

    private final MessagePropertiesService messagePropertiesService;

    @Value("${spring.mail.username}")
    private String username;
    @Override
    public CommonResponse createTicket(TicketDto ticketDto) {
        Ticket ticket = buildTicket(ticketDto);
        Ticket savedTicket = ticketRepository.save(ticket);
        ticketCreationNotification(savedTicket);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, savedTicket);
    }

    private void ticketCreationNotification(Ticket savedTicket) {
        CommonResponse getUserDetails = accountManagementConsumer.getUserDetailsByAccountNumber(savedTicket.getAccountNumber());
        UserDetailDto userDetailDto = objectMapper.convertValue(getUserDetails.getResult(), UserDetailDto.class);
        EmailNotification buildEmail = EmailNotification.builder()
                .sender(username)
                .subject("Banking Platform Notification")
                .body(String.format(messagePropertiesService.getByKey("messages.email.ticket.created"),
                        savedTicket.getTicketReference(),
                        savedTicket.getAccountNumber()))
                .to(userDetailDto.getEmail())
                .build();
        emailService.send(buildEmail);
    }

    @Override
    public CommonResponse updateTicket(String ticketId, TicketDto ticketDto) {

        Optional<Ticket> ticketById = ticketRepository.findById(ticketId);
        if (!ticketById.isPresent()) {
            return new CommonResponse().buildErrorResponse("Ticket Not Found.");
        }
        Ticket updatedTicket = ticketRepository.save(buildUpdateTicket(ticketById.get(), ticketDto));

        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, updatedTicket);
    }

    private Ticket buildUpdateTicket(Ticket ticket, TicketDto ticketDto) {

        ticket.setQueryDescription(ticketDto.getQueryDescription() != null ? ticketDto.getQueryDescription() : ticket.getQueryDescription());
        ticket.setLastUpdated(LocalDateTime.now());
        return ticket;
    }

    private Ticket buildTicket(TicketDto ticketDto) {
        String ticketReference = Utils.generateTicketReference();
        return Ticket.builder()
                .ticketReference(ticketReference)
                .accountNumber(ticketDto.getAccountNumber())
                .queryDescription(ticketDto.getQueryDescription())
                .ticketStatus(TicketStatus.OPEN)
                .dateCreated(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
