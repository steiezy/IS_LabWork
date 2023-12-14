package com.lab.airbnb.controiler.auth;

import com.lab.airbnb.domain.LoginResponse;
import com.lab.airbnb.domain.dto.UserDTO;
import com.lab.airbnb.domain.vo.LoginVo;
import com.lab.airbnb.domain.vo.RegistrationVo;
import com.lab.airbnb.exception.EmailFailureException;
import com.lab.airbnb.exception.UserAlreadyExistException;
import com.lab.airbnb.exception.UserNotVerifiedException;
import com.lab.airbnb.model.User;
import com.lab.airbnb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationVo registrationVo)  {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(registrationVo.getUsername());
        userDTO.setPassword(registrationVo.getPassword());
        userDTO.setPhoneNum(registrationVo.getPhoneNum());
        userDTO.setEmail(registrationVo.getEmail());
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginVo loginVo)  {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(loginVo.getUsername());
        userDTO.setPassword(loginVo.getPassword());
        String token = null;
        try {
            token = userService.loginUser(userDTO);
        } catch (UserNotVerifiedException e) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setSuccess(false);
            //decoupling
            String reason = "USER_NOT_VERIFIED";
            if (e.isNewEmailSent()){
                reason += "_EMAIL_RESENT";
            }
            loginResponse.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (token != null) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(token);
            loginResponse.setSuccess(true);
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/verify")
    public ResponseEntity verifyUser(@RequestParam("token") String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


    }

    @GetMapping("/me")
    public User getLoginUser(@AuthenticationPrincipal User user) {
        return user;
    }
}
