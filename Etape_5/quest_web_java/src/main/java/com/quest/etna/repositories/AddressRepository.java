package com.quest.etna.repositories;


import com.quest.etna.model.Address;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    List<Address> findAll();
    Address findById(int id);
    Optional<Address> findByIdAndUser_id(int id,int user_id);
    List<Address> findByUser_id(int user_id);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.city = ?2 where a.id = ?1")
    void setAddressCityById(int id,String city);
    
    @Transactional
    @Modifying
    @Query("update Address a set a.country = ?2 where a.id = ?1")
    void setAddressCountryById(int id,String country);
    
    @Transactional
    @Modifying
    @Query("update Address a set a.street = ?2 where a.id = ?1")
    void setAddressStreetById(int id,String street);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.postalCode = ?2 where a.id = ?1")
    void setAddressPostalCodeById(int id,String postal_code);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.updatedDate  = ?2 where a.id = ?1")
    void setAddressUpdatedDateById(int id,LocalDateTime time);

    boolean existsById(int id);


    @Transactional
    @Modifying
    void deleteById(int id);


}
