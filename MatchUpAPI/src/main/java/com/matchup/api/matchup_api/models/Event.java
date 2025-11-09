package com.matchup.api.matchup_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.matchup.api.matchup_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Events")
public class Event {
    @Id
    @Column(name = "eve_id")
    private UUID Id;
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
    @Column(name = "eve_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "eve_status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @ManyToOne
    @JoinColumn(name = "eve_address_id", referencedColumnName = "adr_id")
    private Address Address;

    @ManyToOne
    @JoinColumn(name = "eve_sport_id", referencedColumnName = "spo_id")
    private Sport Sport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eve_admin_id", referencedColumnName = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "enrollments", "reports"})
    private User Admin;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Enrollment> enrollments;
}
