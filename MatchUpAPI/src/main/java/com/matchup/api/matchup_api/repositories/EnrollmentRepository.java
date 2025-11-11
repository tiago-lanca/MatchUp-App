package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.event.id = :eventId")
    int countMembersByEventId(UUID eventId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Enrollment e WHERE e.event.id = :eventId AND e.user.id = :userId")
    boolean isUserEnrolled(UUID eventId, UUID userId);

    List<Enrollment> findByEventIdAndUserId(UUID eventId, UUID userId);
    List<Enrollment> findByEventId(UUID eventId);
}
