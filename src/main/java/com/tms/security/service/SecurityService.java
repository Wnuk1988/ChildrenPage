package com.tms.security.service;

import com.tms.exception.SecurityCredentialsForbiddenException;
import com.tms.models.Role;
import com.tms.models.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.security.domain.RegistrationDTO;
import com.tms.security.domain.SecurityChangeRequest;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import com.tms.security.JwtUtils;
import com.tms.security.domain.AuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    @Value("${spring.artemis.broker-url}")
    private String url;
    private final PasswordEncoder passwordEncoder;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserInfo userInfo;
    private final SecurityCredentials securityCredentials;
    private final SmtpMailSender smtpMailSender;

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
            UserInfo userInfoResult = userRepository.save(userInfo);

            securityCredentials.setUserLogin(registrationDTO.getUserLogin());
            securityCredentials.setUserPassword(passwordEncoder.encode(registrationDTO.getUserPassword()));
            securityCredentials.setUserRole(Role.USER);
            securityCredentials.setUserId(userInfoResult.getId());
            securityCredentials.setUserEmail(registrationDTO.getEmail());
            securityCredentials.setActive(false);
            securityCredentials.setActivationCode(UUID.randomUUID().toString());
            log.info("Saving new {}", registrationDTO);
            securityCredentialsRepository.save(securityCredentials);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void updateSecurityCredentials(SecurityChangeRequest securityChangeRequest) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentialsOptional = securityCredentialsRepository.findByUserLogin(login);
        if (securityCredentialsOptional.isPresent()) {
            SecurityCredentials securityDb = securityCredentialsOptional.get();
            if (login.equals(securityDb.getUserLogin()) || (securityDb.getUserRole() == Role.ADMIN)) {
                if (!(securityDb.getUserLogin().equals(securityChangeRequest.getLogin())) && !(securityChangeRequest.getLogin() == null)) {
                    securityDb.setUserLogin(securityChangeRequest.getLogin());
                }
                if (!(securityDb.getUserPassword().equals(securityChangeRequest.getPassword())) && !(securityChangeRequest.getPassword() == null)) {
                    securityDb.setUserPassword(passwordEncoder.encode(securityChangeRequest.getPassword()));
                }
                if (!(securityDb.getUserEmail().equals(securityChangeRequest.getEmail())) && !(securityChangeRequest.getEmail() == null)) {
                    securityDb.setActive(false);
                    securityDb.setActivationCode(UUID.randomUUID().toString());
                    securityDb.setUserEmail(securityChangeRequest.getEmail());
                }
                log.info("Update security user {}", securityChangeRequest);
                securityCredentialsRepository.saveAndFlush(securityDb);
            } else {
                throw new SecurityCredentialsForbiddenException();
            }
        }
    }

    public void activationEmail(String email) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> resultOptional = securityCredentialsRepository.findByUserLogin(login);
        if (resultOptional.isPresent()) {
            SecurityCredentials result = resultOptional.get();
            if (!result.isActive()) {
                if (StringUtils.hasText(result.getUserEmail())) {
                    String message = String.format(
                            "Hello, %s! \n" +
                                    "Welcome to ChildrenPage. Please, visit next link: " + url + "/activate/%s",
                            result.getUserLogin(),
                            result.getActivationCode()
                    );
                    log.info("Activation email {}", email);
                    smtpMailSender.send(result.getUserEmail(), "Activation code", message);
                }
            }
        } else {
            throw new SecurityCredentialsForbiddenException();
        }
    }

    public void activationCode(String code) {
        SecurityCredentials securityCredentials = securityCredentialsRepository.findByActivationCode(code);
        if (securityCredentials == null) {
            throw new SecurityCredentialsForbiddenException();
        }
        securityCredentials.setActive(true);
        securityCredentials.setActivationCode(null);
        securityCredentialsRepository.saveAndFlush(securityCredentials);
    }

    public Optional<List<SecurityCredentials>> findAllBy() {
        return securityCredentialsRepository.findAllBy();
    }
}
