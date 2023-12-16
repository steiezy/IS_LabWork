package com.lab.airbnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false, length = 32)
    private String userId;

    @Column(name = "name", nullable = false, unique = true)
    private String username;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "email",unique = true)
    private String email;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<House> houses = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdTimestamp DESC")
    private List<VerificationToken> verificationTokens = new ArrayList<>();
    @JsonIgnore
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    //0:admin 1:landlord 2:user
    @Column(name = "role", length = 10)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @JsonIgnore
    public Boolean getEmailVerified() {
        return emailVerified;
    }
    @JsonIgnore
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    @JsonIgnore
    public List<VerificationToken> getVerificationTokens() {
        return verificationTokens;
    }
    @JsonIgnore
    public void setVerificationTokens(List<VerificationToken> verificationTokens) {
        this.verificationTokens = verificationTokens;
    }

    @JsonIgnore
    public List<House> getHouseDAOs() {
        return houses;
    }
    @JsonIgnore
    public void setHouseDAOs(List<House> houses) {
        this.houses = houses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}