package com.lab.airbnb.model.dao;

import com.lab.airbnb.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserDAO extends ListCrudRepository<User, String> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByPhoneNum(String phoneNum);

    Optional<User> findByEmail(String email);

}
