package com.matchup.api.matchup_api.dtos;

import com.matchup.api.matchup_api.enums.Status;
import com.matchup.api.matchup_api.models.Address;
import com.matchup.api.matchup_api.models.Event;
import com.matchup.api.matchup_api.models.Sport;
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
    private Address address;
    private LocalDateTime date;
    private Double cost;
    private int duration;
    private Sport sport;
    private String gender;
    private UUID adminId;
    private String adminName;
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
                e.getAddress(),
                e.getDate(),
                e.getCost(),
                e.getDuration(),
                e.getSport(),
                e.getGender(),
                e.getAdmin() != null ? e.getAdmin().getId() : null,
                e.getAdmin() != null ? e.getAdmin().getName() : null,
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
