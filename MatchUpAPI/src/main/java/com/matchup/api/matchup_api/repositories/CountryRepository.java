package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Country findByName(String name);
}
