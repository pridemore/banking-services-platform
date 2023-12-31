package com.example.accountmanagement.service.api;

import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.dto.UserDetailDto;

public interface UserDetailService {
    CommonResponse createUserDetail(UserDetailDto customerDetailDto);

    CommonResponse getUserDetailByNationalIdOrPhoneNumber(String nationalIdOrPhoneNumber);

    CommonResponse updateUserDetail(Long userDetailId,UserDetailDto updateUserDetailDto);
}
