package com.matchup.api.matchup_api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.matchup.api.matchup_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Enrollments")
public class Enrollment {
    @Id
    @Column(name = "enr_id")
    private UUID id;

    @Column(name = "enr_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enr_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enr_eve_id")
    private Event event;
}
