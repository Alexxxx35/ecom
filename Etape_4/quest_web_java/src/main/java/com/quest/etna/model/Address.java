package com.quest.etna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.*;



@Entity @Table(name = "address")
public class Address {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(Integer newID) {
        this.id = newID;
    }


    @ManyToOne @JoinColumn(nullable=true, name = "user_id")
    @JsonBackReference
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Size(max = 100)
    @Column(nullable = false)
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Size(max = 30)
    @Column(nullable = false)
    private String postalCode;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Size(max = 50)
    @Column(nullable = false)
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Size(max = 50)
    @Column(nullable = false)
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Column(columnDefinition = "Datetime")
    private LocalDateTime creationDate;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Column(columnDefinition = "Datetime")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Address() {
    }

    public Address(int id, String Street, String PostalCode, String City, String Country, LocalDateTime CreationDate, LocalDateTime UpdatedDate) {
        setId(id);
        setStreet(street);
        setPostalCode(postalCode);
        setCity(city);
        setCountry(country);
        setCreationDate(creationDate);
        setUpdatedDate(updatedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getId() == address.getId() &&
                getStreet().equals(address.getStreet()) &&
                getPostalCode().equals(address.getPostalCode()) &&
                getCountry().equals(address.getCountry()) &&
                getCity().equals(address.getCity()) &&
                Objects.equals(getCreationDate(), address.getCreationDate()) &&
                Objects.equals(getUpdatedDate(), address.getUpdatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getUser(), getStreet(), getPostalCode(), getCity(), getCountry(), getCreationDate(), getUpdatedDate());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", user=" + user+
                ", road=" + street +
                ", postal_code=" + postalCode +
                ", city=" + city +
                ", country=" + country +
                ", creationDate=" + creationDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
    public String addressDetails() {
        return "{\"id\":\"" + this.getId() + "\", \"street\":\"" + this.getStreet() + "\",\"postalCode\":\"" + this.getPostalCode() + "\",\"city\":\"" + this.getCity() + "\",\"country\":\"" + this.getCountry()+  "\"}";
    }

}
