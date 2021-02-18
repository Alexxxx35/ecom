package com.quest.etna.controller;

import com.quest.etna.model.Address;
import com.quest.etna.repositories.AddressRepository;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.User.UserRole;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestController
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/address/{id}", produces = "application/json")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        User user = getAuthenticatedUser();
        if (addressRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("{\"Error\": \"NOT FOUND\"}", HttpStatus.NOT_FOUND);
        }
        if (user.getRole()== UserRole.ROLE_ADMIN){
            return new ResponseEntity<> (addressRepository.findById(id),HttpStatus.OK);
        } 
        else{
            return new ResponseEntity<> (addressRepository.findByIdAndUser_id(id,user.getId()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/address/")
    public ResponseEntity<Object> findAll() {
        User user = getAuthenticatedUser();  
        if (user.getRole()== UserRole.ROLE_ADMIN){
            return new ResponseEntity<> (addressRepository.findAll(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<> (addressRepository.findByUser_id(user.getId()),HttpStatus.OK); // return only addresses related to user
        }
        
    }

    @PostMapping(value = "/address",produces = "application/json")
    public ResponseEntity<Object> createAddress(@RequestBody Address address) {
        try {
            if (address.getStreet() == null || address.getCity() == null || address.getCountry() == null || address.getPostalCode() == null) {
                throw new Exception("No valid data");
            }

            LocalDateTime creationDatetime = LocalDateTime.now();
            address.setCreationDate(creationDatetime);
            address.setUpdatedDate(creationDatetime);
            User user = getAuthenticatedUser(); 
            address.setUser(user);
            addressRepository.save(address);


            return new ResponseEntity<>(address.addressDetails(), HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/address/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateAddress(@PathVariable int id,@RequestBody Address newAddress) {
        boolean modified =false;
        User user = getAuthenticatedUser();
        Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""+id));

        if (user.getRole()==UserRole.ROLE_ADMIN|| user.getId()==address.getUser().getId()){
            if(newAddress.getCity() != null){
                addressRepository.setAddressCityById(id, newAddress.getCity());
                modified = true;
            } 
            if(newAddress.getCountry() != null) {
                addressRepository.setAddressCountryById(id, newAddress.getCountry());
                modified=true;
            }
            if(newAddress.getPostalCode() != null) {
                addressRepository.setAddressPostalCodeById(id, newAddress.getPostalCode());
                modified = true;
            }
            if(newAddress.getStreet() != null) {
                addressRepository.setAddressStreetById(id, newAddress.getStreet());
                modified = true;
            }
        }
        else {
            return new ResponseEntity<>(addressRepository.findById(id),HttpStatus.UNAUTHORIZED);
        }
        
        if (modified) addressRepository.setAddressUpdatedDateById(id, LocalDateTime.now());
        return new ResponseEntity<>(addressRepository.findById(id),HttpStatus.OK);
    }

    
    @DeleteMapping(value = "/address/{id}",produces = "application/json")
    public ResponseEntity<Object> deleteAddress(@PathVariable int id) {
        User user = getAuthenticatedUser();
        boolean exist= addressRepository.existsById(id);
        System.out.println(exist);

        if (!exist) {
            return new ResponseEntity<>("{\"success\": false }",HttpStatus.NOT_FOUND);
        }
        Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""+id));


        if (user.getRole()==UserRole.ROLE_ADMIN || user.getId()==address.getUser().getId()){
            addressRepository.deleteById(id);  
            return new ResponseEntity<>("{\"success\": true }",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("{\"success\": false }",HttpStatus.UNAUTHORIZED);
        }
        
    }


    public User getAuthenticatedUser(){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            User user = userRepository.findByUsername(userName);
            return user;
    }

}
