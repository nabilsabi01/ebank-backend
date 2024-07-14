package com.banksolutions.ebank.repository;

import com.banksolutions.ebank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNationalId(String nationalId);
    Optional<User> findByEmail(String email);
}