package com.example.accountmanagement.persistance;

import com.example.accountmanagement.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
    Optional<UserDetail> findByNationalId(String nationalId);


}
