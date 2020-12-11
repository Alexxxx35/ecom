package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    /*@PostMapping(value ="/register")
    public User getUsernameandPassword() {
        User user = new User("username", "password");
        userRepository.save(user);
        return user;
    }*/


    @PostMapping(value ="/register" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User user2 = new User(user.getUsername(),user.getPassword());
        List<User> duplicates = findByUsername(user2.getUsername());
        if(!duplicates.isEmpty()){
            return new ResponseEntity<>("{\"Erreur 409 CONFLIT\": \"Nom d'utilisateur déjà utilisé\"}", HttpStatus.CONFLICT);
        }
        try {
            userRepository.save(user2);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Erreur 400\":\""+e.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user2, HttpStatus.CREATED);


    }


    @GetMapping(value = "/user/{id}")
    public Optional<User> findById(@PathVariable Integer id) {
        return userRepository.findById(id);
    }


    @GetMapping(value = "/user/{username}")
    public List<User> findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }


    @GetMapping(value = "/users")
    public Iterable<User> findAllByUsername() {
        return userRepository.findAll();
    }
}


/*
@RestController
public class AuthenticationController {

    @Autowired
    private static UserRepository userRepository;

    @PostMapping(value = "/register/{username}/")
    public ResponseEntity findByUsername(@PathVariable("username") String username){
        List<User> user= userRepository.findByUsername(username);
        if (user.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user,HttpStatus.OK);
    }
}
*/