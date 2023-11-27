package com.example.transactionprocessing.feignclients;

import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.dto.UpdateAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("api-gateway")
public interface AccountManagementConsumer {

    @GetMapping("/account-management-service/api/v1/account/getBalanceByAccount/{accountNumber}")
    CommonResponse getBalanceByAccount(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable("accountNumber") String accountNumber);


    @PutMapping("/account-management-service/api/v1/account/update")
    CommonResponse updateAccount(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,@RequestBody UpdateAccountDto updateBalance);

}
