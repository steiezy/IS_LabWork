package com.lab.airbnb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id", nullable = false)
    private Long tokenId;
    @Lob
    @Column(name = "token_body", nullable = false)
    private String tokenBody;

    @Column(name = "created_timestamp", nullable = false)
    private Timestamp createdTimestamp;

    @Column(name = "owner", length = 32)
    private String owner;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}