package com.example.customersupport.service.api;

import com.example.customersupport.common.response.CommonResponse;
import com.example.customersupport.domain.dto.TicketDto;

public interface CustomerSupportService {

    CommonResponse createTicket(TicketDto ticketDto);
}
