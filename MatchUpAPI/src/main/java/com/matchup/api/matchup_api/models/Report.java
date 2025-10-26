package com.matchup.api.matchup_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @Column(name = "rep_id")
    private UUID id;
    @Column(name = "rep_description")
    private String description;
    @Column(name = "rep_date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "rep_user_id")
    private User user;
}
