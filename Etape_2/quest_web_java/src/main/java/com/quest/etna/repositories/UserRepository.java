package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>  {
    //@Query("SELECT username FROM User WHERE username LIKE %:username%")


    List<User> findByUsername(String username);

    Optional<User> findById(Integer id);
}
