package com.quest.etna.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.quest.etna.model.User;
import com.quest.etna.model.Product;
import com.quest.etna.model.User.UserRole;
import com.quest.etna.repositories.UserRepository;
import com.quest.etna.repositories.ProductRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
@Component    
public class CommandLineAppStartupRunner implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String...args) throws Exception {
        System.out.println("Application Started !!");
        User user = new User(20, "admin", "admin", UserRole.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now());
            String pass = user.getPassword();
            pass = passwordEncoder.encode(pass);
            user.setPassword(pass);
            userRepository.save(user);


        Product p1 = new Product("Sandwich Poulet",11);
        Product p2 = new Product("Sandwich Bacon",7);
        Product p3 = new Product("Sandwich Boeuf",12);
        Product p4 = new Product("Salade césar",8);
        Product p5 = new Product("Baguette tradition",2);
        Product p6 = new Product("Nuke Cola",1);
        Product p7 = new Product("7-Up Mojito",1);
        Product p8 = new Product("Eau minérale",1);
        Product p9 = new Product("Cookie",1);
        Product p10 = new Product("Mille-feuille",2);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        productRepository.save(p5);
        productRepository.save(p6);
        productRepository.save(p7);
        productRepository.save(p8);
        productRepository.save(p9);
        productRepository.save(p10);
        















    }
}