package com.quest.etna.controller;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.Panier;
import com.quest.etna.repositories.PanierRepository;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.validation.*;
import org.springframework.dao.DuplicateKeyException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PanierController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PanierRepository panierRepository;

    @GetMapping(value = "/panier/")
    public ResponseEntity<Object> getAllPanier() {
        return new ResponseEntity<>(panierRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/addPanier")
    public ResponseEntity<Object> addPanier(@Valid @RequestBody Panier panier){
        User currentUser = getAuthenticatedUser();
        try {
            if(currentUser.getId()== panier.getUserId()){
                panierRepository.save(panier);
                return new ResponseEntity<>(panier,HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(panier,HttpStatus.UNAUTHORIZED);
            }
        }
        catch(DuplicateKeyException e) {
            return new ResponseEntity<>("{\"Error 409 CONFLICT\": \"Primary key constraint violated\"}", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error 400\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value="/deletePanier")
    public ResponseEntity<Object> deletePanier(){
        User currentUser = getAuthenticatedUser();
        panierRepository.deleteByUserId(currentUser.getId());
        if(panierRepository.existsByUserId(currentUser.getId())){
            return new ResponseEntity<>("{\"success\": false }", HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>("{\"success\": true }", HttpStatus.OK);

    }

    

    public User getAuthenticatedUser() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = userRepository.findByUsername(userName);
        return user;
    }
    
}
