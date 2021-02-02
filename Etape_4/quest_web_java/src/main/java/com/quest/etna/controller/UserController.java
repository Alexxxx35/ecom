package com.quest.etna.controller;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.User.UserRole;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        //System.out.println(userRepository.findById(id).isPresent());
        if (userRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("{\"Error\": \"NOT FOUND\"}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);

    }
    @GetMapping(value = "/user/")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/user/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody User newUser) {

        User user = getAuthenticatedUser();

        if (newUser.getUsername() != null && (user.getRole() == UserRole.ROLE_ADMIN || user.getId() == id))
            userRepository.setUsernameById(id, newUser.getUsername());
        if (newUser.getRole() != null && user.getRole() == UserRole.ROLE_ADMIN)
            userRepository.setRoleById(id, newUser.getRole());

        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);

    }

    @DeleteMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        User user = getAuthenticatedUser();
        if (id != user.getId() && user.getRole() != UserRole.ROLE_ADMIN) {
            return new ResponseEntity<>("{\"Erreur\": \"Unauthorized access\"}", HttpStatus.UNAUTHORIZED);
        }
        if (user.getId() == id || user.getRole() == UserRole.ROLE_ADMIN) {
            userRepository.deleteById(id);
        }

        if (userRepository.existsById(id)) {
            return new ResponseEntity<>("{\"success\": false }", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("{\"success\": true }", HttpStatus.OK);
        }
    }

    public User getAuthenticatedUser() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = userRepository.findByUsername(userName);
        return user;
    }

}
