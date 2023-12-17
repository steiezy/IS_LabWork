package com.lab.airbnb.controller.auth;


import com.lab.airbnb.domain.vo.RegistrationVo;
import com.lab.airbnb.model.User;
import com.lab.airbnb.model.dao.UserDAO;
import com.lab.airbnb.service.EncryptionService;
import com.lab.airbnb.service.JWTService;
import com.lab.airbnb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
 * This is a fake controller to bypass the email verification process
 */
@RestController
@RequestMapping("/open/v1/auth")
public class FakeAuthController {
    private final UserService userService;

    private final JWTService jwtService;

    private final EncryptionService encryptionService;

    private final UserDAO userDAO;

    public FakeAuthController(UserService userService, JWTService jwtService, EncryptionService encryptionService, UserDAO userDAO) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.encryptionService = encryptionService;
        this.userDAO = userDAO;
    }


    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationVo registrationVo) {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        User user = new User();
        user.setUserId(uuid);
        user.setUsername(registrationVo.getUsername());
        //salt
        user.setPassword(encryptionService.encryptPassword(registrationVo.getPassword()));
        user.setPhoneNum(registrationVo.getPhoneNum());
        user.setEmail(registrationVo.getEmail());
        user.setEmailVerified(true);
        user.setRole("2");

        if (userDAO.findByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username: Username already exists");
        }
        if (userDAO.findByPhoneNum(user.getPhoneNum()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("phoneNum: Phone number already exists");
        }
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email: Email already exists");
        }

        userDAO.save(user);

        String token = jwtService.generateToken(user.getUserId());
        return ResponseEntity.ok(token);

    }

}
