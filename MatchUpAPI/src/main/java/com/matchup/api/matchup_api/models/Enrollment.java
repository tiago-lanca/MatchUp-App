package com.matchup.api.matchup_api.models;

import com.matchup.api.matchup_api.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Enrollments")
public class Enrollment {
    @Id
    private UUID id;

    @Column(name = "enr_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
