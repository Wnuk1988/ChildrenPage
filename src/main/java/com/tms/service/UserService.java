package com.tms.service;

import com.tms.exception.SecurityCredentialsForbiddenException;
import com.tms.exception.UserInfoNotFoundException;
import com.tms.models.Role;
import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.models.request.RequestParametersId;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityCredentialsRepository securityCredentialsRepository;

    public List<UserInfo> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserInfo> getUserById(Integer id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentialsOptional = securityCredentialsRepository.findByUserLogin(login);
        if (securityCredentialsOptional.isPresent()) {
            SecurityCredentials securityCredentials = securityCredentialsOptional.get();
            if (id.equals(securityCredentials.getUserId()) || (securityCredentials.getUserRole() == Role.ADMIN)) {
                return userRepository.findById(id);
            }else {
                throw new UserInfoNotFoundException();
            }
        }
        return Optional.empty();
    }

    public void createUser(UserInfo userInfo) {
        log.info("Saving new {}", userInfo);
        userRepository.save(userInfo);
    }

    public void addFavoritesFile(RequestParametersId requestParametersId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentialsOptional = securityCredentialsRepository.findByUserLogin(login);
        if (securityCredentialsOptional.isPresent()) {
            SecurityCredentials securityCredentials = securityCredentialsOptional.get();
            if (requestParametersId.getUserId().equals(securityCredentials.getUserId()) || (securityCredentials.getUserRole() == Role.ADMIN)) {
                log.info("Saving new favorites file {}", requestParametersId);
                userRepository.addFileToUser(requestParametersId.getUserId(), requestParametersId.getFileId());
            } else {
                throw new SecurityCredentialsForbiddenException();
            }
        }
    }

    public void updateUser(UserInfo userInfo) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentialsOptional = securityCredentialsRepository.findByUserLogin(login);
        if (securityCredentialsOptional.isPresent()) {
            SecurityCredentials securityCredentials = securityCredentialsOptional.get();
            if (userInfo.getId().equals(securityCredentials.getUserId()) || (securityCredentials.getUserRole() == Role.ADMIN)) {
                log.info("Update user {}", userInfo);
                userRepository.saveAndFlush(userInfo);
            } else {
                throw new SecurityCredentialsForbiddenException();
            }
        }
    }

    public void deleteUserById(Integer id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentialsOptional = securityCredentialsRepository.findByUserLogin(login);
        if (securityCredentialsOptional.isPresent()) {
            SecurityCredentials securityCredentials = securityCredentialsOptional.get();
            if (id.equals(securityCredentials.getUserId()) || (securityCredentials.getUserRole() == Role.ADMIN)) {
                log.info("Delete user {}", id);
                userRepository.deleteById(id);
            } else {
                throw new SecurityCredentialsForbiddenException();
            }
        }
    }

    public void deleteByFavoritesFile(RequestParametersId requestParametersId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentialsOptional = securityCredentialsRepository.findByUserLogin(login);
        if (securityCredentialsOptional.isPresent()) {
            SecurityCredentials securityCredentials = securityCredentialsOptional.get();
            if (requestParametersId.getUserId().equals(securityCredentials.getUserId()) || (securityCredentials.getUserRole() == Role.ADMIN)) {
                log.info("Delete favorites file {}", requestParametersId);
                userRepository.deleteByFavoritesFile(requestParametersId.getUserId(), requestParametersId.getFileId());
            } else {
                throw new SecurityCredentialsForbiddenException();
            }
        }
    }
}
