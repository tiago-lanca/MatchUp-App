package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventRepository extends CrudRepository<Event, UUID> {

}
