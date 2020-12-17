package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        //Date sqlDate=new Date(new java.util.Date().getTime());
        try {
            boolean duplicata = userRepository.existsByUsername(user.getUsername());
            if (duplicata) {
                throw new DuplicateKeyException("duplicata");
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
            return new ResponseEntity<>("{\"Error 400\":\""+e.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
        }
    }

    

    /*
    @PostMapping(value ="/register" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        //Date sqlDate=new Date(new java.util.Date().getTime());
        LocalDateTime creationDatetime = LocalDateTime.now();
        try{
            boolean duplicata = userRepository.existsByUsername(user.getUsername());
            if (duplicata) {
                throw new DuplicateKeyException("duplicata");
            }
        }catch(DuplicateKeyException e)
        //User user2 = new User(user.getUsername(),user.getPassword());
        //List<User> duplicates = findByUsername(user2.getUsername());
        //if(!duplicates.isEmpty()){
        {return new ResponseEntity<>("{\"Error 409 CONFLICT\": \"username already used\"}", HttpStatus.CONFLICT);
        }
        try {
            user.setRole(User.UserRole.ROLE_USER);
            user.setCreationDate(creationDatetime);
            user.setUpdatedDate(creationDatetime);
            userRepository.save(user);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\""+e.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(, HttpStatus.CREATED);


    }*/


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