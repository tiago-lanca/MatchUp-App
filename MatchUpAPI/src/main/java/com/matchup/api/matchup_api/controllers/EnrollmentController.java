package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Enrollment;
import com.matchup.api.matchup_api.repositories.EnrollmentRepository;
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

@RequestMapping(path="/api/enrollments")
public class EnrollmentController {
    private Logger logger = LoggerFactory.getLogger(EnrollmentController.class);
    private EnrollmentRepository _enrollmentRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository)
    {
        _enrollmentRepository = enrollmentRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Enrollment> getEnrollments() {
        logger.info("Getting all enrollments");
        return _enrollmentRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Enrollment getEnrollmentById(@PathVariable("id")UUID id) {
        logger.info("Getting enrollment by id");
        return _enrollmentRepository.findById(id).orElse(null);
    }

    @GetMapping(path = "/event/{id}/count-members")
    public int countMembersInEvent(@PathVariable("id") UUID eventId) {
        logger.info("Counting members in enrollment with id: " + eventId);
        return _enrollmentRepository.countMembersByEventId(eventId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        logger.info("Creating new enrollment");
        if(enrollment == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Enrollment newEnrollment = _enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEnrollment);
    }
}
