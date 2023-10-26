package com.tms.controller;

import com.tms.exception.UserInfoNotFoundException;
import com.tms.models.UserInfo;
import com.tms.models.request.RequestParametersId;
import com.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "We receive information about all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to obtain a list of users"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping
    public ResponseEntity<List<UserInfo>> getUsers() {
        List<UserInfo> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @Operation(summary = "Getting information about the user", description = "We receive information about the user, we need to provide his id for input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to obtain user data"),
            @ApiResponse(responseCode = "400", description = "A syntax error was detected in the client's request"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "404", description = "No corresponding resource was found at the specified URL"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable Integer id) {
        UserInfo userInfo = userService.getUserById(id).orElseThrow(UserInfoNotFoundException::new);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @Operation(summary = "Adding a user", description = "We are adding a user, we need to pass json UserInfo to the input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "We have successfully created a user"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "409", description = "Failed to create user"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserInfo userInfo) {
        userService.createUser(userInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Adding a file to the user's favorites", description = "We are adding a file, we need to provide json RequestParametersId as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "You have successfully added favorites"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PostMapping("/favorites")
    public ResponseEntity<HttpStatus> addFavoritesFile(@RequestBody RequestParametersId requestParametersId) {
        userService.addFavoritesFile(requestParametersId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Changing the user", description = "We are changing the user, we need to pass json UserInfo to the input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Your request has been successfully processed"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserInfo userInfo) {
        userService.updateUser(userInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting a user", description = "We are deleting a user, we need to provide his id at the login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "A syntax error was detected in the client's request"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Removing a file from a favorite user", description = "We are adding a file, we need to provide json RequestParametersId as input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite file deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @DeleteMapping("/favorites")
    public ResponseEntity<HttpStatus> deleteByFavoritesFile(@RequestBody RequestParametersId requestParametersId) {
        userService.deleteByFavoritesFile(requestParametersId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
