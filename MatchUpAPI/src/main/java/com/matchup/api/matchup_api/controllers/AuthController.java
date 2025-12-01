package com.matchup.api.matchup_api.controllers;

import com.matchup.api.matchup_api.models.User;
import com.matchup.api.matchup_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController

@RequestMapping(path="/api/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository _userRepository;

    public AuthController(UserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getLogin(@RequestBody User user) {
        logger.info("Trying to login user");

        Optional<User> existingUser = _userRepository.getUserByEmail(user.getEmail());
        if(existingUser.isPresent()){
            User foundUser = existingUser.get();
            if(foundUser.getPasswordHash().equals(user.getPasswordHash())){
                return ResponseEntity.ok(foundUser);
            } else {
                logger.info("Invalid password for user: " + user.getEmail());
                return ResponseEntity.status(401).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
