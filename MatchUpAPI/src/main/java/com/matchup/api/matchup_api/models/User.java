package com.matchup.api.matchup_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_city")
    private String city;
    @Column(name = "user_mobilePhone")
    private String mobilePhone;
    @Column(name = "user_passwordHash")
    private String passwordHash;
    @Column(name = "user_gender")
    private String gender;
    @Column(name = "user_profilePicture")
    private byte[] profilePicture;
    @Column(name = "user_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_country_id", referencedColumnName = "cou_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "user_favSport_id", referencedColumnName = "spo_id")
    private Sport favoriteSport;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Report> reports;

}
