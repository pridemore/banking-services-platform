package com.example.customersupport.service.impl;

import com.example.customersupport.common.SystemConstants;
import com.example.customersupport.common.Utils;
import com.example.customersupport.common.response.CommonResponse;
import com.example.customersupport.domain.Ticket;
import com.example.customersupport.domain.dto.TicketDto;
import com.example.customersupport.persistance.TicketRepository;
import com.example.customersupport.service.api.CustomerSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class CustomerSupportServiceImpl implements CustomerSupportService {
    private final TicketRepository ticketRepository;

    @Override
    public CommonResponse createTicket(TicketDto ticketDto) {
        Ticket ticket = buildTicket(ticketDto);
        Ticket savedTicket = ticketRepository.save(ticket);
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, savedTicket);
    }

    private Ticket buildTicket(TicketDto ticketDto) {
        String ticketReference= Utils.generateTicketReference();
        return Ticket.builder()
                .ticketReference(ticketReference)
                .accountNumber(ticketDto.getAccountNumber())
                .queryDescription(ticketDto.getQueryDescription())
                .dateCreated(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
