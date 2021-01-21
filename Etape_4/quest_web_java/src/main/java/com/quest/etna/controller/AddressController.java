package com.quest.etna.controller;

import com.quest.etna.model.Address;
import com.quest.etna.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(value = "/address/{id}")
    public Optional<Address> findById(@PathVariable Integer id) {
         return addressRepository.findById(id);
    }

    @GetMapping(value = "/address/")
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @PostMapping(value = "/address", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createAddress(@RequestBody Address address) {
        try {
            if (address.getRoad() == null || address.getCity() == null || address.getCountry() == null || address.getPostalCode() == null) {
                throw new Exception("No valid data");
            }
            boolean duplicate = addressRepository.existsByCity(address.getCity());
            if (duplicate) {
                throw new DuplicateKeyException("Duplicate");
            }
            LocalDateTime creationDatetime = LocalDateTime.now();
            address.setCreationDate(creationDatetime);
            address.setUpdatedDate(creationDatetime);
            addressRepository.save(address);
            return new ResponseEntity<>(address, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("{\"Error 409 CONFLICT\": \"address already used\"}", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/address/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateAddress(@RequestBody Address newAddress) {
        try {
            boolean duplicate = addressRepository.existsByCity(newAddress.getCity());
            if (duplicate) {
                addressRepository.save(newAddress);
                return new ResponseEntity<>(newAddress, HttpStatus.NO_CONTENT);
            }
            addressRepository.save(newAddress);
            return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/address/{id}")
    public ResponseEntity<Integer> deleteAddress(@PathVariable Integer id) {
        addressRepository.delete(findById(id));
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*
    @PutMapping(value = "/address/{id}", consumes = "application/json", produces = "application/json")
    Optional<Address> replaceAddress(@RequestBody Address newAddress, @PathVariable Integer id) {
        return addressRepository.findById(id);
    }


     */


}
