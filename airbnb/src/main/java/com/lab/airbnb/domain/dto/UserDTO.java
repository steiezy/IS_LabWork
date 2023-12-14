package com.lab.airbnb.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lab.airbnb.model.House;
import com.lab.airbnb.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO implements Serializable {
    private String userId;
    private String username;
    @JsonIgnore
    private String password;
    private String phoneNum;
    private String email;
    private List<House> houses = new ArrayList<>();
}