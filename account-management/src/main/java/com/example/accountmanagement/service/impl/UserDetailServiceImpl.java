package com.example.accountmanagement.service.impl;

import com.example.accountmanagement.common.SystemConstants;
import com.example.accountmanagement.common.enums.Status;
import com.example.accountmanagement.common.response.CommonResponse;
import com.example.accountmanagement.domain.UserDetail;
import com.example.accountmanagement.domain.dto.UserDetailDto;
import com.example.accountmanagement.persistance.UserDetailRepository;
import com.example.accountmanagement.service.api.UserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;

    @Override
    public CommonResponse createUserDetail(UserDetailDto userDetailDto) {

        Optional<UserDetail> existingUser = userDetailRepository.findByNationalId(userDetailDto.getNationalId());

        if (existingUser.isPresent()) {
            return new CommonResponse().buildErrorResponse("User Already Exist.");
        }

        UserDetail savedUserDetail = userDetailRepository.save(buildUserDetails(userDetailDto));

        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, savedUserDetail);
    }

    @Override
    public CommonResponse getUserDetailByNationalIdOrPhoneNumber(String nationalIdOrPhoneNumber) {

        UserDetail foundUserDetail = userDetailRepository.findByNationalIdOrPhoneNumber(nationalIdOrPhoneNumber, nationalIdOrPhoneNumber);
        if (Objects.isNull(foundUserDetail)) {
            return new CommonResponse().buildErrorResponse("User Details Not Found.");
        }
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS, foundUserDetail);
    }

    @Override
    public CommonResponse updateUserDetail(Long userDetailId,UserDetailDto updateUserDetailDto) {
        Optional<UserDetail> foundUserDetail = userDetailRepository.findById(userDetailId);
        if (!foundUserDetail.isPresent()) {
            return new CommonResponse().buildErrorResponse("User Not Found.");
        }
        UserDetail updatedUserDetail = userDetailRepository.save(buildUpdateUserDetail(foundUserDetail.get(),updateUserDetailDto));
        return new CommonResponse().buildSuccessResponse(SystemConstants.SUCCESS,updatedUserDetail);
    }

    private UserDetail buildUpdateUserDetail(UserDetail userDetail, UserDetailDto updateUserDetailDto) {
        userDetail.setName(updateUserDetailDto.getName() !=null ? updateUserDetailDto.getName() :userDetail.getName());
        userDetail.setSurname(updateUserDetailDto.getSurname() !=null ? updateUserDetailDto.getSurname() : userDetail.getSurname());
        userDetail.setNationalId(updateUserDetailDto.getNationalId() !=null ? updateUserDetailDto.getNationalId() : userDetail.getNationalId());
        userDetail.setGender(updateUserDetailDto.getGender() !=null ? updateUserDetailDto.getGender() : userDetail.getGender());
        userDetail.setDob(updateUserDetailDto.getDob() !=null ? updateUserDetailDto.getDob():userDetail.getDob());
        userDetail.setAddress(updateUserDetailDto.getAddress() !=null ?updateUserDetailDto.getAddress() : userDetail.getAddress());
        userDetail.setPhoneNumber(updateUserDetailDto.getPhoneNumber() !=null ? updateUserDetailDto.getPhoneNumber() : userDetail.getPhoneNumber());
        userDetail.setEmail(updateUserDetailDto.getEmail()!=null ? updateUserDetailDto.getEmail() : userDetail.getEmail());
        userDetail.setLastUpdated(OffsetDateTime.now());
        return userDetail;
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
                .email(userDetailDto.getEmail())
                .status(Status.ACTIVE)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
    }
}
