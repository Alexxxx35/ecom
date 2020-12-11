package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User getUsernameandPassword() {
        User user = new User("username", "password");
        userRepository.save(user);
        return user;
    }

    @GetMapping(value = "/user/{id}")
    public Optional<User> findById(@PathVariable Integer id) {
        return userRepository.findById(id);
    }


    @GetMapping(value = "/user{username}")
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