package com.tms.controller;

import com.tms.exception.UserInfoNotFoundException;
import com.tms.models.UserInfo;
import com.tms.models.request.RequestParametersId;
import com.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получаем информацию обо всех пользователях")
    @GetMapping
    public ResponseEntity<List<UserInfo>> getUsers() {
        List<UserInfo> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @Operation(summary = "Получаем информацию об пользователе", description = "Мы получаем информацию об пользователе, на вход нужно предать его id")
    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable Integer id) {
        UserInfo userInfo = userService.getUserById(id).orElseThrow(UserInfoNotFoundException::new);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @Operation(summary = "Добавляем пользователя", description = "Мы добавляем пользователя, на вход нужно предать json UserInfo")
    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserInfo userInfo) {
        userService.createUser(userInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Добавляем файл в избранное пользователя", description = "Мы добавляем файл, на вход нужно предать json RequestParametersId")
    @PostMapping("/favorites")
    public ResponseEntity<HttpStatus> addFavoritesFile(@RequestBody RequestParametersId requestParametersId) {
        userService.addFavoritesFile(requestParametersId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Изменяем пользователя", description = "Мы изменяем пользователя, на вход нужно предать json UserInfo")
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserInfo userInfo) {
        userService.updateUser(userInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Удаляем пользователя", description = "Мы удаляем пользователя, на вход нужно предать его id")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Удаляем файл из избранного пользователя", description = "Мы добавляем файл, на вход нужно предать json RequestParametersId")
    @DeleteMapping("/favorites")
    public ResponseEntity<HttpStatus> deleteByFavoritesFile(@RequestBody RequestParametersId requestParametersId) {
        userService.deleteByFavoritesFile(requestParametersId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
