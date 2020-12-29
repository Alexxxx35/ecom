package com.quest.etna.repositories;

import com.quest.etna.model.User;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    Optional<User> findById(Integer id);

    boolean existsByUsername(String username);
}
