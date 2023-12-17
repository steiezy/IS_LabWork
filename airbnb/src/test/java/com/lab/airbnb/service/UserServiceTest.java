package com.lab.airbnb.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.lab.airbnb.domain.dto.UserDTO;
import com.lab.airbnb.exception.EmailFailureException;
import com.lab.airbnb.exception.UserNotVerifiedException;
import com.lab.airbnb.exception.UserPhoneNumAlreadyExistException;
import com.lab.airbnb.exception.UsernameAlreadyExistException;
import com.lab.airbnb.model.VerificationToken;
import com.lab.airbnb.model.dao.VerificationTokenDAO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UserServiceTest {
    @RegisterExtension
    public static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("spring", "password"))
            .withPerMethodLifecycle(true);


    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;


    @BeforeEach
    public void startMailServer() {
        greenMail.start();
        System.out.println("GreenMail server started on port " + greenMail.getSmtp().getPort());
    }

    @AfterEach
    public void stopMailServer() {
        greenMail.stop();
        System.out.println("GreenMail server stopped");
    }

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("userA");
        userDTO.setEmail("spring@localhost");
        userDTO.setPhoneNum("12343678901");
        Assertions.assertThrows(UsernameAlreadyExistException.class, () -> {
            userService.registerUser(userDTO);
        }, "Username should already be in use");
        userDTO.setUsername("UserServiceTest$TestRegisterUser");
        userDTO.setPhoneNum("1234567890");
        Assertions.assertThrows(UserPhoneNumAlreadyExistException.class, () -> {
            userService.registerUser(userDTO);
        }, "PhoneNum should already be in use");
        userDTO.setPhoneNum("12343678901");
        userDTO.setPassword("userAPassword");
        Assertions.assertDoesNotThrow(() -> {
            userService.registerUser(userDTO);
        }, "Should register user successfully");
        Assertions.assertEquals(userDTO.getEmail(), greenMail.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());
    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("userA-nonExist");
        userDTO.setPassword("userXPassword");
        Assertions.assertNull(userService.loginUser(userDTO),
                "Should return null when user not exist");
        userDTO.setUsername("userA");
        Assertions.assertNull(userService.loginUser(userDTO), "The password is wrong");
        userDTO.setPassword("userAPassword");
        Assertions.assertNotNull(userService.loginUser(userDTO), "Should login user successfully");

        userDTO.setUsername("userB");
        try {
            userService.loginUser(userDTO);
            Assertions.fail("Should throw UserNotVerifiedException");
        } catch (UserNotVerifiedException e) {
            Assertions.assertTrue(e.isNewEmailSent(), "Should send new email");
            Assertions.assertEquals(1, greenMail.getReceivedMessages().length,
                    "Should send one email");
        }

        try {
            userService.loginUser(userDTO);
            Assertions.fail("Should throw UserNotVerifiedException");
        } catch (UserNotVerifiedException e) {
            Assertions.assertFalse(e.isNewEmailSent(), "Should not send new email");
            Assertions.assertEquals(1, greenMail.getReceivedMessages().length,
                    "Should send one email");
        }
    }

    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("tokenA"),
                "Should return false when token not exist");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("userB");
        userDTO.setPassword("userAPassword");
        try {
            userService.loginUser(userDTO);
            Assertions.fail("Should throw UserNotVerifiedException");
        } catch (UserNotVerifiedException e) {
            //应该catch在这里。。。
        } catch (EmailFailureException e) {
            List<VerificationToken> verificationTokens = verificationTokenDAO.findByUser_UserIdOrderByTokenIdDesc("2");
            String token = verificationTokens.get(0).getTokenBody();
            Assertions.assertTrue(userService.verifyUser(token),
                    "Should return true when token exist");
            Assertions.assertNotNull(userDTO.getUsername(), "Should login user successfully");

        }

    }

}
