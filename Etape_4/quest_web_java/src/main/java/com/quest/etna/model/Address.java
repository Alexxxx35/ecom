package com.quest.etna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity(name = "address")
public class Address {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(Integer newID) {
        this.id = newID;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Size(max = 100)
    @Column(nullable = false)
    private String road;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
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
    private LocalDateTime updatedDate;

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Address() {
    }

    public Address(Integer id, String Road, String PostalCode, String City, String Country, LocalDateTime CreationDate, LocalDateTime UpdatedDate) {
        setId(id);
        setRoad(road);
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
                getRoad().equals(address.getRoad()) &&
                getPostalCode().equals(address.getPostalCode()) &&
                getCountry().equals(address.getCountry()) &&
                getCity().equals(address.getCity()) &&
                Objects.equals(getCreationDate(), address.getCreationDate()) &&
                Objects.equals(getUpdatedDate(), address.getUpdatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getUser(), getRoad(), getPostalCode(), getCity(), getCountry(), getCreationDate(), getUpdatedDate());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", user=" + user +
                ", road=" + road +
                ", postal_code=" + postalCode +
                ", city=" + city +
                ", country=" + country +
                ", creationDate=" + creationDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

}
