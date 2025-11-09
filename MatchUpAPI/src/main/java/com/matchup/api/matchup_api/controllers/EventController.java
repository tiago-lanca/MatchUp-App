package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.dtos.EventDTO;
import com.matchup.api.matchup_api.models.Event;
import com.matchup.api.matchup_api.models.User;
import com.matchup.api.matchup_api.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping(path="/api/events")
public class EventController {
    private Logger logger = LoggerFactory.getLogger(EventController.class);
    private EventRepository _eventRepository;

    public EventController(EventRepository eventRepository)
    {
        _eventRepository = eventRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEvents() {
        logger.info("Getting all events");

        return _eventRepository.findAll();

        /*return _eventRepository.findAll()
                .stream()
                .map(EventDTO::fromEntity)
                .toList();*/
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getEventById(@PathVariable("id") UUID id) {
        logger.info("Getting event with id: " + id);

        return _eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        /*return _eventRepository.findById(id)
                .map(EventDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));*/
    }

    @GetMapping(path = "/{id}/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getEventAdmin(@PathVariable("id") UUID eventId) {
        logger.info("Getting event admin:");

        return _eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId))
                .getAdmin();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Event> createEvent(@RequestBody Event event) {
        logger.info("Creating new event");
        if (event == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Event newEvent = _eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);

        /*Event newEvent = _eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventDTO.fromEntity(newEvent));*/
    }

}
