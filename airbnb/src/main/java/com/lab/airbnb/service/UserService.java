package com.lab.airbnb.service;

import com.lab.airbnb.domain.PasswordResetBody;
import com.lab.airbnb.domain.dto.UserDTO;
import com.lab.airbnb.exception.*;
import com.lab.airbnb.model.User;
import com.lab.airbnb.model.VerificationToken;
import com.lab.airbnb.model.dao.UserDAO;
import com.lab.airbnb.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserService {
    @Value("${spring.email.expireTime}")
    private int expireTime;

    private UserDAO userDAO;
    private VerificationTokenDAO verificationTokenDAO;
    private EncryptionService encryptionService;

    private EmailService emailService;
    private JWTService jwtService;

    public UserService(UserDAO userDAO, VerificationTokenDAO verificationTokenDAO, EncryptionService encryptionService, EmailService emailService, JWTService jwtService) {
        this.userDAO = userDAO;
        this.verificationTokenDAO = verificationTokenDAO;
        this.encryptionService = encryptionService;
        this.emailService = emailService;
        this.jwtService = jwtService;
    }


    public void registerUser(UserDTO userDTO) throws UserAlreadyExistException, EmailFailureException {
        if (userDAO.findByUsernameIgnoreCase(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException();
        }
        if (userDAO.findByPhoneNum(userDTO.getPhoneNum()).isPresent()) {
            throw new UserPhoneNumAlreadyExistException();
        }
        if (userDAO.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistException();
        }
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        User user = new User();
        user.setUserId(uuid);
        user.setUsername(userDTO.getUsername());
        user.setPassword(encryptionService.encryptPassword(userDTO.getPassword()));
        user.setPhoneNum(userDTO.getPhoneNum());
        user.setEmail(userDTO.getEmail());
        user.setRole("2");
        userDAO.save(user);
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        verificationTokenDAO.save(verificationToken);
    }


    public String loginUser(UserDTO userDTO) throws UserNotVerifiedException, EmailFailureException {
        Optional<User> opUser = userDAO.findByUsernameIgnoreCase(userDTO.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (encryptionService.checkPassword(userDTO.getPassword(), user.getPassword())) {
                if (user.getEmailVerified()) {
                    return jwtService.generateToken(user.getUserId());
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - expireTime));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }


    private VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setTokenBody(jwtService.generateVerificationToken(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        verificationToken.setOwner(user.getUserId());
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opVerificationToken = verificationTokenDAO.findByTokenBody(token);
        if (opVerificationToken.isPresent()) {
            VerificationToken verificationToken = opVerificationToken.get();
            if (verificationToken.getCreatedTimestamp().getTime() + expireTime < System.currentTimeMillis()) {
                verificationTokenDAO.delete(verificationToken);
                return false;
            }
            User user = verificationToken.getUser();
            if (!user.getEmailVerified()) {
                user.setEmailVerified(true);
                userDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    public void forgotPassword(String email) throws EmailFailureException, EmailNotFoundException {
        Optional<User> opUser = userDAO.findByEmail(email);
        if (opUser.isPresent()) {
            User user = opUser.get();
            String token = jwtService.generatePasswordResetToken(user);
            emailService.sendResetPasswordEmail(user, token);
        } else {
            throw new EmailNotFoundException();
        }
    }


    public void resetPassword(PasswordResetBody body) throws PasswordResetTokenInvalidException {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<User> opUser = userDAO.findByEmail(email);
        if (opUser.isPresent()) {
            User user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            userDAO.save(user);

        } else {
            throw new PasswordResetTokenInvalidException();
        }
    }

    public User findByUserId(String userId) {
        Optional<User> opUser = userDAO.findByUserId(userId);
        return opUser.orElse(null);
    }

}
