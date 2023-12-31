package com.example.customersupport.controller;

import com.example.customersupport.common.response.CommonResponse;
import com.example.customersupport.domain.dto.TicketDto;
import com.example.customersupport.service.api.CustomerSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customerSupport")
public class CustomerSupportController {

    private final CustomerSupportService customerSupportService;

    @PostMapping("/createTicket")
    public CommonResponse createTicket(@RequestBody TicketDto ticketDto){
        return customerSupportService.createTicket(ticketDto);
    }

    @PutMapping("/updateTicket/{ticketId}")
    public CommonResponse updateTicket(@PathVariable("ticketId") String ticketId, @RequestBody TicketDto ticketDto){
        return customerSupportService.updateTicket(ticketId,ticketDto);
    }

}
