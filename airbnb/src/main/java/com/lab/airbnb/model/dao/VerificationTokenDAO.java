package com.lab.airbnb.model.dao;

import com.lab.airbnb.model.User;
import com.lab.airbnb.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, String> {
    Optional<VerificationToken> findByTokenBody(String tokenBody);

    void deleteByUser(User user);

    List<VerificationToken> findByUser_UserIdOrderByTokenIdDesc(String userId);





}
