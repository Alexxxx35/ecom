package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>  {
    @Query("SELECT username FROM User WHERE username LIKE %:username%")
    public List<User> findByUsername(String username);
}
