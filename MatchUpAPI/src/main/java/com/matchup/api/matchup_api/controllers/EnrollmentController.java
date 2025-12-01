package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Enrollment;
import com.matchup.api.matchup_api.models.Event;
import com.matchup.api.matchup_api.repositories.EnrollmentRepository;
import com.matchup.api.matchup_api.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        return _enrollmentRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Enrollment getEnrollmentById(@PathVariable("id")UUID id) {

        return _enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + id));
    }



    @GetMapping(path = "/event/{id}/count-members")
    public int countMembersInEvent(@PathVariable("id") UUID eventId) {
        return _enrollmentRepository.countMembersByEventId(eventId);
    }

    @GetMapping(path = "/event/{eventId}/user/{userId}/is-enrolled")
    public boolean isUserEnrolled(@PathVariable("eventId") UUID eventId, @PathVariable("userId") UUID userId) {
        return _enrollmentRepository.isUserEnrolled(eventId, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        if(enrollment == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Enrollment newEnrollment = _enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEnrollment);

        /*return ResponseEntity.status(HttpStatus.CREATED).body(EnrollmentDTO.fromEntity(newEnrollment));*/
    }

    @DeleteMapping(path = "/event/{eventId}/user/{userId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("eventId") UUID eventId, @PathVariable("userId") UUID userId) {
        // Search all enrollments which matches eventId and userId
        List<Enrollment> enrollments = _enrollmentRepository.findByEventIdAndUserId(eventId, userId);

        if (enrollments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Delete all found enrollments
        _enrollmentRepository.deleteAll(enrollments);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
