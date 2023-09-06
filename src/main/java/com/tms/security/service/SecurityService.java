package com.tms.security.service;

import com.tms.models.Role;
import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.security.domain.RegistrationDTO;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import com.tms.security.JwtUtils;
import com.tms.security.domain.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserInfo userInfo;
    private final SecurityCredentials securityCredentials;

    public String generateToken(AuthRequest authRequest) {
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByUserLogin(authRequest.getLogin());
        if (credentials.isPresent() && passwordEncoder.matches(authRequest.getPassword(), credentials.get().getUserPassword())) {
            return jwtUtils.generateJwtToken(authRequest.getLogin());
        }
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDTO registrationDTO) {
        Optional<SecurityCredentials> result = securityCredentialsRepository.findByUserLogin(registrationDTO.getUserLogin());
        if (result.isEmpty()) {
            userInfo.setFirstName(registrationDTO.getFirstName());
            userInfo.setLastName(registrationDTO.getLastName());
            userInfo.setDateOfBirth(registrationDTO.getDateOfBirth());
            userInfo.setEmail(registrationDTO.getEmail());
            UserInfo userInfoResult = userRepository.save(userInfo);

            securityCredentials.setUserLogin(registrationDTO.getUserLogin());
            securityCredentials.setUserPassword(passwordEncoder.encode(registrationDTO.getUserPassword()));
            securityCredentials.setUserRole(Role.USER);
            securityCredentials.setUserId(userInfoResult.getId());
            securityCredentialsRepository.save(securityCredentials);
        }
    }
}
