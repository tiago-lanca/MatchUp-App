package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.Address;
import com.matchup.api.matchup_api.repositories.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
