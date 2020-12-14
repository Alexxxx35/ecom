package com.quest.etna.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

import java.util.Objects;
@Entity
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer newID) {
        this.id = newID;
    }


    @Column(nullable = false, unique = true)
    @Size(max = 255)
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }


    @Column(nullable = false)
    @Size(max = 255)
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN
    }

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole newRole) {
        this.role = newRole;
    }

    @CreationTimestamp
    @Column()
    private Date creationDate;

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date newcreationDate) {
        this.creationDate = newcreationDate;
    }

    @UpdateTimestamp
    @Column()
    private Date updatedDate;

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date newupdatedDate) {
        this.updatedDate = newupdatedDate;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role=UserRole.ROLE_USER;
        }

    public User() {

    }
    public User(Integer id, String username, String password, UserRole role, Date creationDate, Date updatedDate) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setRole(role);
        setCreationDate(creationDate);
        setUpdatedDate(updatedDate);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getUsername().equals(user.getUsername()) &&
                getPassword().equals(user.getPassword()) &&
                getRole() == user.getRole() &&
                Objects.equals(getCreationDate(), user.getCreationDate()) &&
                Objects.equals(getUpdatedDate(), user.getUpdatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getRole(), getCreationDate(), getUpdatedDate());
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", creationDate=" + creationDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
