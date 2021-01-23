package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findAll();
    Optional<User> findById(Integer id);
    //Optional<User> findByAddress(String address);
    boolean existsByUsername(String username);
}
