package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Address;
import com.matchup.api.matchup_api.repositories.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping(path="/api/addresses")
public class AddressController {
    private Logger logger = LoggerFactory.getLogger(AddressController.class);
    private AddressRepository _addressRepository;

    public AddressController(AddressRepository addressRepository)
    {
        _addressRepository = addressRepository;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Address> getAddresses() {
        logger.info("Getting all addresses");
        return _addressRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Address> createAddress(@RequestBody Address address) {
        logger.info("Creating new address");

        if(address == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Address newAddress = _addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }
}
