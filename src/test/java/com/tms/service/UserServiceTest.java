package com.tms.service;

import com.tms.models.DescriptionFile;
import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

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
    public void getUserTest() {
        securityCredentialsRepository.findByUserLogin("Login");
        Mockito.verify(securityCredentialsRepository, Mockito.times(1)).findByUserLogin(anyString());
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID_VALUE);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(userInfo));
        userService.getUserById(ID_VALUE);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void createUser() {
        userService.createUser(new UserInfo());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateUser() {
        userService.updateUser(new UserInfo());
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteUser() {
        userService.deleteUserById(ID_VALUE);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(any());
    }
}
