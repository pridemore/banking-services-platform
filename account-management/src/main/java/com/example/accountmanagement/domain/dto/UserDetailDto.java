package com.example.accountmanagement.domain.dto;

import com.example.accountmanagement.domain.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDetailDto {

    private String name;

    private String surname;

    private String nationalId;

    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String address;

    private String phoneNumber;

    private String email;

}
