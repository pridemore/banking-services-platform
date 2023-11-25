package com.example.accountmanagement.service.impl;

import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.persistance.UserDetailRepository;
import com.example.accountmanagement.service.api.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;
    @Override
    public UserDetail createUserDetail(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }
}
