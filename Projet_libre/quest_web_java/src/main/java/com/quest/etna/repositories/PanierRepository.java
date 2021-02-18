package com.quest.etna.repositories;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import javax.transaction.Transactional;
import java.util.List;
import com.quest.etna.model.Panier;


@Repository
public interface PanierRepository extends CrudRepository<Panier, Integer> {

    List<Panier> findAll();
    List<Panier> findByUserId(int userId);
    List<Panier> findByProductId(int productId);

    boolean existsByUserId(int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByUserId(int userId);

    










    
}
