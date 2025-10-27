package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Sport;
import com.matchup.api.matchup_api.repositories.SportRepository;
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

@RequestMapping(path="/api/sports")
public class SportController {
    private Logger logger = LoggerFactory.getLogger(SportController.class);
    private SportRepository _sportRepository;

    public SportController(SportRepository sportRepository) {
        _sportRepository = sportRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sport> getSports() {
        logger.info("Getting all sports");
        return _sportRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sport getSportById(@PathVariable("id")UUID id) {
        logger.info("Getting sport with id: " + id);
        return _sportRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Sport> createSport(@RequestBody Sport sport) {
        logger.info("Creating new sport");

        if(sport == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Sport newSport = _sportRepository.save(sport);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSport);
    }
}
