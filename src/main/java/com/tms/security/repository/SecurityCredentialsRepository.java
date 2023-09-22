package com.tms.security.repository;

import com.tms.security.domain.SecurityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityCredentialsRepository extends JpaRepository<SecurityCredentials, Integer> {
    Optional<SecurityCredentials> findByUserLogin(String login);
    SecurityCredentials findByActivationCode(String code);
    Optional<SecurityCredentials> findByUserEmail(String email);
}
