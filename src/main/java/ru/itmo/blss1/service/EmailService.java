package ru.itmo.blss1.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itmo.blss1.data.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
@EnableScheduling
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserService userService;

    @Scheduled(cron = "1 */3 * * * *")
    public void sendMessage() {
        String prop = System.getProperty("send.emails");
        if (prop.equals("true")) {
            List<User> userList = (ArrayList<User>) userService.getAll();
            Logger logger = LoggerFactory.getLogger(EmailService.class);
            logger.info("try to seng something");
            for (User user:userList) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("pinterest updates");
                message.setText("Hey, check today's new pins!!");
                logger.info(user.getLogin());
                emailSender.send(message);
            }
        }
    }
}
