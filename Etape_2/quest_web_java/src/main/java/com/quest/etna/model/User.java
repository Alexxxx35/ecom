package com.quest.etna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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


    @Column(name = "password", nullable = false)
    @Size(max = 255)
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    private enum UserRole {
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

    @Column()
    private LocalDate creationDate;

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDate newcreationDate) {
        this.creationDate = newcreationDate;
    }

    @Column()
    private LocalDate updatedDate;

    public LocalDate getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(LocalDate newupdatedDate) {
        this.updatedDate = newupdatedDate;
    }

    public User() {
    }

    public User(Integer id, String username, String password, UserRole role, LocalDate creationDate, LocalDate updatedDate) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setRole(role);
        setCreationDate(creationDate);
        setUpdatedDate(updatedDate);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
