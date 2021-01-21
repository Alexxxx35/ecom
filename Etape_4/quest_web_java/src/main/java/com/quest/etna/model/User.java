package com.quest.etna.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;


@Entity
@Table(name="User")
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(Integer newID) {
        this.id = newID;
    }

    @OneToMany(mappedBy = "User", fetch = FetchType.LAZY)
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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

  /*  public Collection<Object> getRoles() {
    }
*/

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN
    }

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserRole getRole() {
        return this.role;
    }

    public String getRoleString() {
        if (this.role == UserRole.ROLE_ADMIN) return "admin";
        else return "user";
    }

    public void setRole(UserRole newRole) {
        this.role = newRole;
    }


    @Column(columnDefinition = "Datetime")
    private LocalDateTime creationDate;

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDateTime newcreationDate) {
        this.creationDate = newcreationDate;
    }

    @Column(columnDefinition = "Datetime")
    private LocalDateTime updatedDate;

    public LocalDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(LocalDateTime newupdatedDate) {
        this.updatedDate = newupdatedDate;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.ROLE_USER;
    }

    public User() {

    }

    public User(Integer id, String username, String password, UserRole role, LocalDateTime creationDate, LocalDateTime updatedDate) {
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
        return getId() == user.getId() &&
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

    public String userDetails() {
        return "{\"username\":\"" + this.getUsername() + "\",\"role\":\"" + this.getRole() + "\"}";
    }
}

