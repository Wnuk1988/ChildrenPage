package com.tms.controller;

import com.tms.models.DescriptionFile;
import com.tms.models.UserInfo;
import com.tms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/favorites/{userId},{fileId}")
    public ResponseEntity<HttpStatus> addFavoritesFile(@PathVariable Integer userId, @PathVariable Integer fileId) {
        Optional<UserInfo> userInfoUpdated = userService.getUserById(userId);
        userService.addFavoritesFile(userId, fileId);
        Optional<UserInfo> userInfo = userService.getUserById(userId);
        if (userInfo.isEmpty() && userInfoUpdated.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/favorites/{favoritesFile}")
    public ResponseEntity<HttpStatus> deleteByFavoritesFile(@PathVariable List<DescriptionFile> favoritesFile) {
        userService.deleteByFavoritesFile(favoritesFile);
        boolean descriptionFileDelete = favoritesFile.isEmpty();
        if (descriptionFileDelete) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> getUsers() {
        List<UserInfo> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable Integer id) {
        Optional<UserInfo> userInfoOptional = userService.getUserById(id);
        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserInfo userInfo) {
        UserInfo userInfoSaved = userService.createUser(userInfo);
        Optional<UserInfo> userInfoResult = userService.getUserById(userInfoSaved.getId());
        if (userInfoResult.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserInfo userInfo) {
        userService.updateUser(userInfo);
        Optional<UserInfo> userInfoUpdatedOptional = userService.getUserById(userInfo.getId());
        if (userInfoUpdatedOptional.isPresent()) {
            UserInfo userInfoUpdated = userInfoUpdatedOptional.get();
            if (userInfo.equals(userInfoUpdated)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        Optional<UserInfo> userInfoUpdated = userService.getUserById(id);
        userService.deleteUserById(id);
        Optional<UserInfo> userInfo = userService.getUserById(id);
        if (userInfo.isEmpty() && userInfoUpdated.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
