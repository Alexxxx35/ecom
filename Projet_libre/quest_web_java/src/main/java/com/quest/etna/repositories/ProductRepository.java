package com.quest.etna.repositories;

import com.quest.etna.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product findByNomProduit(String name);
    
    List<Product> findAll();

    Optional<Product> findById(Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteById(int id);

    boolean existsById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.nomProduit= ?2 where p.id = ?1")
    void setNomProduitById(int id,String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.prix= ?2 where p.id = ?1")
    void setPrixById(int id,int prix);





















}
