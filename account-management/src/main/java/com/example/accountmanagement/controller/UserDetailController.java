package com.example.accountmanagement.controller;

import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.domain.dto.UserDetailDto;
import com.example.accountmanagement.service.api.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userDetail")
@RequiredArgsConstructor
public class UserDetailController {
    private final UserDetailService userDetailService;

    @PostMapping("/create")
    public CommonResponse createUserDetails(@RequestBody UserDetailDto userDetailDto){
        return userDetailService.createUserDetail(userDetailDto);
    }
}

