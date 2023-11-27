package com.example.customersupport.feignclients;

import com.example.customersupport.common.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("api-gateway")
public interface AccountManagementConsumer {
    @GetMapping("/account-management-service/api/v1/account/getUserDetailsByAccount/{accountNumber}")
    CommonResponse getUserDetailsByAccountNumber(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable("accountNumber") String accountNumber);
}
