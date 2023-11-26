package com.example.accountmanagement.service.impl;

import com.example.accountmanagement.common.SystemConstants;
import com.example.accountmanagement.common.enums.Status;
import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.Account;
import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.domain.dto.UserDetailDto;
import com.example.accountmanagement.persistance.UserDetailRepository;
import com.example.accountmanagement.service.api.UserDetailService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;

    @Override
    public CommonResponse createUserDetail(UserDetailDto userDetailDto) {

        Optional<UserDetail> existingUser = userDetailRepository.findByNationalId(userDetailDto.getNationalId());

        if (existingUser.isPresent()) {
            return new CommonResponse().buildErrorResponse("User Already Exist.");
        }

        UserDetail savedUserDetail =userDetailRepository.save(buildUserDetails(userDetailDto));

        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS,savedUserDetail) ;
    }

    @Override
    public CommonResponse getUserDetailByNationalIdOrPhoneNumber(String nationalIdOrPhoneNumber) {

        UserDetail foundUserDetail=userDetailRepository.findByNationalIdOrPhoneNumber(nationalIdOrPhoneNumber,nationalIdOrPhoneNumber);
        if(Objects.isNull(foundUserDetail)){
            return new CommonResponse().buildErrorResponse("User Details Not Found.");
        }
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS,foundUserDetail);
    }

    private UserDetail buildUserDetails(UserDetailDto userDetailDto) {
        return UserDetail.builder()
                .name(userDetailDto.getName())
                .surname(userDetailDto.getSurname())
                .nationalId(userDetailDto.getNationalId())
                .gender(userDetailDto.getGender())
                .dob(userDetailDto.getDob())
                .address(userDetailDto.getAddress())
                .phoneNumber(userDetailDto.getPhoneNumber())
                .status(Status.ACTIVE)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
    }
}
