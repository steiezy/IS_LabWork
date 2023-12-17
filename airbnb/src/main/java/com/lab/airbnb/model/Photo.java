package com.lab.airbnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "t_photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @Column(name = "file_url",nullable = false)
    private String fileUrl;


}