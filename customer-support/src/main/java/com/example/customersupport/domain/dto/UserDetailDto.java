package com.example.customersupport.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
    private String name;
    private String surname;
    private String nationalId;
    private String gender;
    private LocalDate dob;
    private String address;
    private String phoneNumber;
    private String email;
}
