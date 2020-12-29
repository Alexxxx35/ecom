package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    //@PostMapping(value="/authenticate", consumes = "application/json", produces = "application/json")

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            if (user.getPassword() == null) {
                throw new Exception("no password");
            }
            if (user.getUsername() == null) {
                throw new Exception("no username");
            }
            boolean duplicate = userRepository.existsByUsername(user.getUsername());
            if (duplicate) {
                throw new DuplicateKeyException("Duplicate");
            }
            user.setRole(User.UserRole.ROLE_USER);
            LocalDateTime creationDatetime = LocalDateTime.now();
            user.setCreationDate(creationDatetime);
            user.setUpdatedDate(creationDatetime);
            userRepository.save(user);
            return new ResponseEntity<>(user.userDetails(), HttpStatus.CREATED);

        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("{\"Error 409 CONFLICT\": \"username already used\"}", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
/*
    @GetMapping(value = "/user/{id}")
    public Optional<User> findById(@PathVariable Integer id) {
        return userRepository.findById(id);
    }


    @GetMapping(value = "/user/{username}")
    public User findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }


    @GetMapping(value = "/users")
    public Iterable<User> findAllByUsername() {
        return userRepository.findAll();
    }
}
*/