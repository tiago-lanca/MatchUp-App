package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.event_id = :eventId")
    int countMembersByEventId(UUID eventId);
}
