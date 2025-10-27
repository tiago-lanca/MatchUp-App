package com.matchup.api.matchup_api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping(path="/api/addresses")
public class AddressController {
    private Logger logger = LoggerFactory.getLogger(AddressController.class);
}
