package com.example.accountmanagement.domain.dto;

import com.example.accountmanagement.domain.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

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

}
