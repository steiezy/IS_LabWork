package com.lab.airbnb.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.lab.airbnb.domain.dto.UserDTO;
import com.lab.airbnb.domain.vo.RegistrationVo;
import com.lab.airbnb.exception.EmailFailureException;
import com.lab.airbnb.exception.UserNotVerifiedException;
import com.lab.airbnb.exception.UserPhoneNumAlreadyExistException;
import com.lab.airbnb.exception.UsernameAlreadyExistException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @RegisterExtension
    public static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("spring", "password"))
            .withPerMethodLifecycle(true);

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("userA");
        userDTO.setEmail("UserServiceTest$TestRegisterUser@junit.com");
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
        Assertions.assertNull(userService.loginUser(userDTO),"The password is wrong");
        userDTO.setPassword("userAPassword");
        Assertions.assertNotNull(userService.loginUser(userDTO),"Should login user successfully");

        userDTO.setUsername("userB");
        try {
            userService.loginUser(userDTO);
            Assertions.fail("Should throw UserNotVerifiedException");
        } catch (UserNotVerifiedException e) {
            Assertions.assertTrue(e.isNewEmailSent(),"Should send new email");
            Assertions.assertEquals(1, greenMail.getReceivedMessages().length,
                    "Should send one email");
        }

        try {
            userService.loginUser(userDTO);
            Assertions.fail("Should throw UserNotVerifiedException");
        } catch (UserNotVerifiedException e) {
            Assertions.assertFalse(e.isNewEmailSent(),"Should not send new email");
            Assertions.assertEquals(1, greenMail.getReceivedMessages().length,
                    "Should send one email");
        }
    }



}
