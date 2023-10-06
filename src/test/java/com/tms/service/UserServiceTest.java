package com.tms.service;

import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final Integer ID_VALUE = 5;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityCredentialsRepository securityCredentialsRepository;

    @BeforeAll
    public static void beforeAll() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    public void getUsers() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID_VALUE);
        List<UserInfo> userList = new ArrayList<>();
        userList.add(userInfo);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<UserInfo> result = userService.getUsers();

        assertThat(result, allOf(notNullValue(), equalTo(userList)));
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getUserById() {
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setUserLogin("Login");
        securityCredentials.setUserId(ID_VALUE);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID_VALUE);

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(userInfo));
        Mockito.when(securityCredentialsRepository.findByUserLogin(anyString())).thenReturn(Optional.of(securityCredentials));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("LOGIN");

        userService.getUserById(ID_VALUE);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(securityCredentialsRepository, Mockito.times(1)).findByUserLogin(anyString());
    }

    @Test
    public void createUser() {
        userService.createUser(new UserInfo());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateUser() {
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setUserLogin("Login");
        securityCredentials.setUserId(ID_VALUE);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID_VALUE);

        Mockito.when(securityCredentialsRepository.findByUserLogin(anyString())).thenReturn(Optional.of(securityCredentials));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("LOGIN");

        userService.updateUser(userInfo);
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(any());
        Mockito.verify(securityCredentialsRepository, Mockito.times(1)).findByUserLogin(anyString());
    }

    @Test
    public void deleteUserById() {
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setUserLogin("Login");
        securityCredentials.setUserId(ID_VALUE);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID_VALUE);

        Mockito.when(securityCredentialsRepository.findByUserLogin(anyString())).thenReturn(Optional.of(securityCredentials));
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("LOGIN");

        userService.deleteUserById(ID_VALUE);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(any());
        Mockito.verify(securityCredentialsRepository, Mockito.times(1)).findByUserLogin(anyString());
    }
}
