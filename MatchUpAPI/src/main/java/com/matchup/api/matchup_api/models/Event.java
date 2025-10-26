package com.matchup.api.matchup_api.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Events")
public class Event {
    @Id
    @Column(name = "eve_id")
    private UUID Id = UUID.randomUUID();
    @Column(name = "eve_name")
    private String Name;
    @Column(name = "eve_date")
    private LocalDateTime Date;
    @Column(name = "eve_cost")
    private Double Cost;
    @Column(name = "eve_duration")
    private int Duration;
    @Column(name = "eve_gender")
    private String Gender;
    @Column(name = "eve_maxMembers")
    private int MaxMembers;
    @Column(name = "eve_notes")
    private String Notes;

    @ManyToOne
    @JoinColumn(name = "eve_address_id", referencedColumnName = "adr_id")
    private Address Address;

    @ManyToOne
    @JoinColumn(name = "eve_sport_id", referencedColumnName = "spo_id")
    private Sport Sport;

    @ManyToOne
    @JoinColumn(name = "eve_admin", referencedColumnName = "user_id")
    private User Admin;
}
