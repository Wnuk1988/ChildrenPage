package com.tms.service;

import com.tms.models.DescriptionFile;
import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
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

    public void addFavoritesFile(Integer userId, Integer fileId) {
        userRepository.addFileToUser(userId, fileId);
    }

    public void deleteByFavoritesFile(List<DescriptionFile> favoritesFile) {
        log.info("Delete favoritesFile {}", favoritesFile);
        userRepository.deleteByFavoritesFile(favoritesFile);
    }

    public List<UserInfo> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserInfo> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public UserInfo createUser(UserInfo userInfo) {
        log.info("Saving new {}", userInfo);
        return userRepository.save(userInfo);
    }

    public void updateUser(UserInfo userInfo) {
        Optional<UserInfo> fromDb = getUserById(userInfo.getId());
        if (fromDb.isPresent()) {
            UserInfo newUser = fromDb.get();
            newUser.setFirstName(userInfo.getFirstName());
            newUser.setLastName(userInfo.getLastName());
            newUser.setLogin(userInfo.getLogin());
            newUser.setPassword(userInfo.getPassword());
            newUser.setEmail(userInfo.getEmail());
            log.info("Update user {}", userInfo);
            userRepository.save(newUser);
        } else {
            fromDb.isEmpty();
        }
    }

    public void deleteUserById(Integer id) {
        log.info("Delete user {}", id);
        userRepository.deleteById(id);
    }
}
