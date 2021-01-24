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
    Optional<Address> findById(int id);
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
    @Query("update Address a set a.road = ?2 where a.id = ?1")
    void setAddressRoadById(int id,String road);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.postalCode = ?2 where a.id = ?1")
    void setAddressPostalCodeById(int id,String postal_code);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.updatedDate  = ?2 where a.id = ?1")
    void setAddressUpdatedDateById(int id,LocalDateTime time);


    @Transactional
    @Modifying
    @Query("update Address a set a.city = ?3 where a.id = ?1 and a.user = ?2")
    void setAddressCityByIdAndUser_id(int id,int user_id,String city);
    
    @Transactional
    @Modifying
    @Query("update Address a set a.country = ?3 where a.id = ?1 and a.user = ?2")
    void setAddressCountryByIdAndUser_id(int id,int user_id,String country);
    
    @Transactional
    @Modifying
    @Query("update Address a set a.road = ?3 where a.id = ?1 and a.user = ?2")
    void setAddressRoadByIdAndUser_id(int id,int user_id,String road);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.postalCode = ?3 where a.id = ?1 and a.user = ?2")
    void setAddressPostalCodeByIdAndUser_id(int id,int user_id,String postal_code);

    
    @Transactional
    @Modifying
    @Query("update Address a set a.updatedDate  = ?3 where a.id = ?1 and a.user = ?2")
    void setAddressUpdatedDateByIdAndUser_id(int id,int user_id,LocalDateTime time);



    boolean existsByCity(String city);
    boolean deleteById(Optional<Address> address);
}
