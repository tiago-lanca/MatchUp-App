package com.matchup.api.matchup_api.repositories;

import com.matchup.api.matchup_api.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

}
