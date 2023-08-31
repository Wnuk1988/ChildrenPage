package com.tms.repository;

import com.tms.models.SecurityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityCredentialsRepository extends JpaRepository<SecurityCredentials, Integer> {
    SecurityCredentials findByUserLogin(String login);
}
