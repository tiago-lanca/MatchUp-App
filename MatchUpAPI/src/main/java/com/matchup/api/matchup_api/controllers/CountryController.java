package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Country;
import com.matchup.api.matchup_api.repositories.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping(path="/api/countries")
public class CountryController {
    private Logger logger = LoggerFactory.getLogger(CountryController.class);
    private CountryRepository _countryRepository;

    public CountryController(CountryRepository countryRepository)
    {
        _countryRepository = countryRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getCountries() {
        logger.info("Getting all countries");
        return _countryRepository.findAll();
    }
}
