package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/register/")
    public User getUsernameandPassword() {
        User user = new User("username", "password");
        userRepository.save(user);
        return user;
    }
}

/*
@RestController
public class AuthenticationController {

    @Autowired
    private static UserRepository userRepository;

    @RequestMapping(value = "/register/{username}", method = RequestMethod.POST)
    public ResponseEntity<User> findByUsername(@PathVariable("username") String username){
        List<User> user= userRepository.findByUsername(username);
        if (user.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user,HttpStatus.OK);
    }
}
*/