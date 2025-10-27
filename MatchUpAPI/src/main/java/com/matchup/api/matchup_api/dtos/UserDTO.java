package com.matchup.api.matchup_api.dtos;

import com.matchup.api.matchup_api.models.Country;
import com.matchup.api.matchup_api.models.Sport;
import com.matchup.api.matchup_api.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String city;
    private String mobilePhone;
    private String gender;
    private Country country;
    private Sport favoriteSport;
    private List<EnrollmentDTO> enrollments;

    public static UserDTO fromEntity(User u) {
        return new UserDTO(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getCity(),
                u.getMobilePhone(),
                u.getGender(),
                u.getCountry() != null ? u.getCountry() : null,
                u.getFavoriteSport() != null ? u.getFavoriteSport() : null,
                u.getEnrollments() != null
                        ? u.getEnrollments().stream()
                        .map(EnrollmentDTO::fromEntity)
                        .collect(Collectors.toList())
                        : null
        );
    }
}
