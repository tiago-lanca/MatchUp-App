package com.matchup.api.matchup_api.dtos;

import com.matchup.api.matchup_api.enums.Status;
import com.matchup.api.matchup_api.models.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private UUID id;
    private String name;
    private LocalDateTime date;
    private Double cost;
    private int duration;
    private String gender;
    private int maxMembers;
    private String notes;
    private Status status;
    private List<EnrollmentDTO> enrollments;

    public static EventDTO fromEntity(Event e) {
        List<EnrollmentDTO> enrollmentDTOs = e.getEnrollments().stream()
                .map(EnrollmentDTO::fromEntity)
                .toList();

        return new EventDTO(
                e.getId(),
                e.getName(),
                e.getDate(),
                e.getCost(),
                e.getDuration(),
                e.getGender(),
                e.getMaxMembers(),
                e.getNotes(),
                e.getStatus(),
                e.getEnrollments() != null ?
                        e.getEnrollments().stream()
                                .map(EnrollmentDTO::fromEntity)
                                .collect(Collectors.toList())
                        : null
        );
    }
}
