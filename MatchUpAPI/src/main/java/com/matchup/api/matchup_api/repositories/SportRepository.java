package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SportRepository extends JpaRepository<Sport, UUID> {
}
