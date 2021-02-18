package com.quest.etna.model;


import javax.persistence.*;
import java.util.Objects;
import javax.persistence.IdClass;
import java.io.Serializable;


@Entity @Table(name="panier")
@IdClass(PanierId.class)
public class Panier implements Serializable{
    private static final long serialVersionUID = -97L;
    @Id
    private int userId;
    @Id
    private int productId;

    @Column(nullable = false)
    private int quantity;



    public int getUserId(){
        return this.userId;
    }

    public int getProductId(){
        return this.productId;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }
    public void setProductId(int productId){
        this.productId=productId;
    }

    public Panier(){

    }

    public Panier(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }



    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier= (Panier) o;
        return userId == panier.userId &&
                productId == panier.productId &&
                quantity == panier.quantity;
    }

    public int hashCode() {
        return Objects.hash(userId, productId,quantity);
    }


    
}
