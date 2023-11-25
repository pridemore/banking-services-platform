package com.example.accountmanagement.controller;

import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.service.api.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userDetail")
@RequiredArgsConstructor
public class UserDetailController {
    private final UserDetailService userDetailService;

    @PostMapping("/create")
    public UserDetail createUserDetails(@RequestBody UserDetail userDetail){
        return userDetailService.createUserDetail(userDetail);
    }
}

