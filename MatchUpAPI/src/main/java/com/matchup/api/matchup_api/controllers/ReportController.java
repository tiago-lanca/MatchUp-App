package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Report;
import com.matchup.api.matchup_api.repositories.ReportRepository;
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

@RequestMapping(path="/api/report")
public class ReportController {
    private Logger logger = LoggerFactory.getLogger(ReportController.class);
    private ReportRepository _reportRepository;

    public ReportController(ReportRepository reportRepository) {
        _reportRepository = reportRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Report> getReports() {
        return _reportRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Report getReportById(@PathVariable("id") UUID id) {
        return _reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = _reportRepository.save(report);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
    }

    @DeleteMapping(path = "/{id}")
    public HttpEntity<Void> deleteReport(@PathVariable("id") UUID id) {
        try {
            Report report = _reportRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + id));

            _reportRepository.delete(report);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e){
            logger.error("Error deleting report with id: " + id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

