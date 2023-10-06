package com.tms.service;

import com.tms.exception.UserInfoNotFoundException;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.service.SecurityService;
import com.tms.security.service.SmtpMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final static String CRON_ONE_TIME_TO_MONTH = "0 0 18 15 * ?";
    private final SecurityService securityService;
    private final SmtpMailSender smtpMailSender;

    @Scheduled(cron = CRON_ONE_TIME_TO_MONTH)
    private void sendingMessageToUser() {
        Optional<List<SecurityCredentials>> allSecurityCredentialsOptional = securityService.findAllBy();
        if (allSecurityCredentialsOptional.isPresent()) {
            List<SecurityCredentials> resultList = allSecurityCredentialsOptional.get();
            for (SecurityCredentials user : resultList) {
                if (user.isActive()) {
                    String message = String.format(
                            "Hello, %s! \n" +
                                    "Go to the site and find for yourself what is not new! With respect \n" +
                                    " Your ChildrenPage.",
                            user.getUserLogin()
                    );
                    log.info("Mailing to the use {}", user);
                    smtpMailSender.send(user.getUserEmail(), "We are glad that you are with us", message);
                }
            }
        } else {
            log.info("securityService.findAllByUserEmail() return empty list!");
            throw new UserInfoNotFoundException();
        }
    }
}
