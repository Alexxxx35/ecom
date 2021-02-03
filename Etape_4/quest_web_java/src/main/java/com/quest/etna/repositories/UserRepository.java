package com.quest.etna.repositories;

import com.quest.etna.model.User;
import com.quest.etna.model.User.UserRole;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findAll();
    Optional<User> findById(Integer id);
    //Optional<User> findByAddress(String address);
    boolean existsByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.username= ?2 where u.id = ?1")
    void setUsernameById(int id,String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.role= ?2 where u.id = ?1")
    void setRoleById(int id,UserRole userRole);


    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteById(int id);


    boolean existsById(int id);


    
}
