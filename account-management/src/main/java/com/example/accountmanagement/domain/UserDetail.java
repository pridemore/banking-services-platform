package com.example.accountmanagement.domain;

import com.example.accountmanagement.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long userDetailId;

    private String name;

    private String surname;

    @Column(unique = true)
    private String nationalId;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dob;

    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    @Email
    private String email;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "userDetail")
    @JsonManagedReference
    private List<Account> accounts;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private OffsetDateTime dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;
}
