package com.quest.etna.controller;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/user/{id}")
    public Optional<User> findById(@PathVariable Integer id) {
        return userRepository.findById(id);
    }

    @GetMapping(value = "/users/")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /*@PutMapping(value = "/user{id}", produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

    }*/
}
