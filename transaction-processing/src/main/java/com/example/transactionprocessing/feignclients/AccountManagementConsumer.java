package com.example.transactionprocessing.feignclients;

import com.example.transactionprocessing.common.response.CommonResponse;
import com.example.transactionprocessing.domain.dto.UpdateAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ACCOUNT-MANAGEMENT-SERVICE")
public interface AccountManagementConsumer {

    @GetMapping("/api/v1/account/getBalanceByAccount/{accountNumber}")
    CommonResponse getBalanceByAccount(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/api/v1/account/update")
    CommonResponse updateAccount(@RequestBody UpdateAccountDto updateBalance);
}
