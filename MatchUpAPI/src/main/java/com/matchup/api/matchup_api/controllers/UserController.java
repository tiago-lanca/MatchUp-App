package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.User;
import com.matchup.api.matchup_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@RequestMapping(path="/api/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository _userRepository;

    public UserController(UserRepository userRepository) {
        _userRepository = userRepository;
    }

    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        logger.info("UserController Initializing");
        return _userRepository.findAll();
    }
}
