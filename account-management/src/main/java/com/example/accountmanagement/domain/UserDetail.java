package com.example.accountmanagement.domain;

import com.example.accountmanagement.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate dob;

    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true,mappedBy = "userDetail")
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
