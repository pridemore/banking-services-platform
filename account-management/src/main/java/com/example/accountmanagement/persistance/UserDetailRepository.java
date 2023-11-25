package com.example.accountmanagement.persistance;

import com.example.accountmanagement.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
}
