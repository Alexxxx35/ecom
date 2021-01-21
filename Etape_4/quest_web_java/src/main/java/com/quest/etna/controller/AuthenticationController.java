package com.quest.etna.controller;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.config.JwtUserDetailsService;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
    public String authenticate(@RequestBody User user )throws Exception {
        JwtUserDetails jwtUserDetails = new JwtUserDetailsService(userRepository).loadUserByUsername(user.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtUserDetails.getUsername(),
                    jwtUserDetails.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    return new JwtTokenUtil().generateToken(jwtUserDetails);
    }

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

            String pass = user.getPassword();
            BCryptPasswordEncoder bEncoder= new BCryptPasswordEncoder();
            pass = bEncoder.encode(pass);
            user.setPassword(pass);
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