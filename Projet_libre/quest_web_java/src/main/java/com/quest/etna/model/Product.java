package com.quest.etna.model;

import javax.persistence.*;
import java.util.Objects;



@Entity @Table(name="product")
public class Product {
    
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int newID) {
        this.id = newID;
    }

    @Column(nullable = false, unique = true)
    private String nomProduit;

    public String getNomProduit(){
        return nomProduit;
    }

    public void setNomProduit(String nom){
        nomProduit=nom;
    }

    @Column(nullable = false)
    private int prix;

    public int getPrix(){
        return prix;
    }
    public void setPrix(int prix){
        this.prix=prix;
    }
    

    public Product(){

    }

    public Product(String nom,int prix){
        setNomProduit(nom);
        setPrix(prix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product p = (Product) o;
        return getId() == p.getId() &&
                getNomProduit().equals(p.getNomProduit()) &&
                getPrix() == p.getPrix() ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomProduit(), getPrix());
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nomProduit='" + nomProduit+ '\'' +
                ", prix=" + prix +
                '}';
    }
}
