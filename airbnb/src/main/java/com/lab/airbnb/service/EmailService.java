package com.lab.airbnb.service;

import com.lab.airbnb.exception.EmailFailureException;
import com.lab.airbnb.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.email.host}")
    private String host;
    @Value("${app.frontend.url}")
    private String frontendUrl;
    private  JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    private SimpleMailMessage createMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(host);
        return message;
    }

    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
            SimpleMailMessage message = createMessage();
            message.setTo(verificationToken.getUser().getEmail());
            message.setSubject("Verification Email");
            message.setText("Please click the link below to verify your email:\n" +
                    frontendUrl + "/api/v1/auth/verify?token=" + verificationToken.getTokenBody());
            try {
                javaMailSender.send(message);
            }catch (MailException e){
                throw new EmailFailureException();
            }

    }
}
