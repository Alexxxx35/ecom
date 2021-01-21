package com.quest.etna.repositories;


import com.quest.etna.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    List<Address> findAll();
    Optional<Address> findById(Integer id);
    boolean existsByCity(String city);


}
