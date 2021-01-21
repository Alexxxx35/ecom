package com.quest.etna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/authenticate" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> authenticate(@RequestBody User user )throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }


        final JwtUserDetails jwtUserDetails = userService.loadUserByUsername(user.getUsername());
        final String token= jwtTokenUtil.generateToken(jwtUserDetails);
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,String> hash = new HashMap<String,String>();
        hash.put("token", token);
        String json = mapper.writeValueAsString(hash);
        return ResponseEntity.status(HttpStatus.OK).body(json);




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
            //BCryptPasswordEncoder bEncoder= new BCryptPasswordEncoder();
            //pass = bEncoder.encode(pass);
            pass = passwordEncoder.encode(pass);
            user.setPassword(pass);
            userRepository.save(user);
            return new ResponseEntity<>(user.userDetails(), HttpStatus.CREATED);

        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("{\"Error 409 CONFLICT\": \"username already used\"}", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<Object> me() {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            User user = userRepository.findByUsername(userName);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user.userDetails());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Error("Error"));
        }
    }
}