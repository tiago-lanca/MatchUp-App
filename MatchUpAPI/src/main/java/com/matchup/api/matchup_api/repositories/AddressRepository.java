package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
