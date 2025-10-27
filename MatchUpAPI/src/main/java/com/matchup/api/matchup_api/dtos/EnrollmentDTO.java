package com.matchup.api.matchup_api.dtos;

import com.matchup.api.matchup_api.models.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private UUID id;
    private UUID userId;
    private String user_name;
    private UUID eventId;
    private String event_name;
    private LocalDateTime createdAt;

    public static EnrollmentDTO fromEntity(Enrollment e) {
        return new EnrollmentDTO(
                e.getId(),
                e.getUser() != null ? e.getUser().getId() : null,
                e.getUser() != null ? e.getUser().getName() : null,
                e.getEvent() != null ? e.getEvent().getId() : null,
                e.getEvent() != null ? e.getEvent().getName() : null,
                e.getCreatedAt()
        );
    }
}