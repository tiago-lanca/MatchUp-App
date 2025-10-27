package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.dtos.UserDTO;
import com.matchup.api.matchup_api.models.User;
import com.matchup.api.matchup_api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController

@RequestMapping(path="/api/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository _userRepository;

    public UserController(UserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getUsers() {
        logger.info("Getting all users");
        return _userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();
    }

    @GetMapping(path = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@PathVariable("id") UUID id) {
        logger.info("Getting user with id: " + id);
        return _userRepository.findById(id)
                .map(UserDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating new user");
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        User newUser = _userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
