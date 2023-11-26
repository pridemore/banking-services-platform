package com.example.customersupport.controller;

import com.example.customersupport.common.response.CommonResponse;
import com.example.customersupport.domain.dto.TicketDto;
import com.example.customersupport.service.api.CustomerSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customerSupport")
public class CustomerSupportController {

    private final CustomerSupportService customerSupportService;

    @PostMapping("/createTicket")
    public CommonResponse createTicket(@RequestBody TicketDto ticketDto){
        return customerSupportService.createTicket(ticketDto);
    }
}
