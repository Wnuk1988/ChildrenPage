package com.tms.service;

import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.models.request.RequestParametersId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserInfo> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserInfo> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public void createUser(UserInfo userInfo) {
        log.info("Saving new {}", userInfo);
        userRepository.save(userInfo);
    }

    public void addFavoritesFile(RequestParametersId requestParametersId) {
        log.info("Saving new favorites file {}", requestParametersId);
        userRepository.addFileToUser(requestParametersId.getUserId(), requestParametersId.getFileId());
    }

    public void updateUser(UserInfo userInfo) {
        log.info("Update user {}", userInfo);
        userRepository.saveAndFlush(userInfo);
    }

    public void deleteUserById(Integer id) {
        log.info("Delete user {}", id);
        userRepository.deleteById(id);
    }

    public void deleteByFavoritesFile(RequestParametersId requestParametersId) {
        log.info("Delete favorites file {}", requestParametersId);
        userRepository.deleteByFavoritesFile(requestParametersId.getUserId(), requestParametersId.getFileId());
    }
}
