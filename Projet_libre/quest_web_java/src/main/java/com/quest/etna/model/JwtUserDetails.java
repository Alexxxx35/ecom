package com.quest.etna.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import com.quest.etna.model.User.UserRole;

import java.util.ArrayList;


public class JwtUserDetails implements UserDetails {
    private static final long serialVersionUID = -907L;

    private final User user;

    public JwtUserDetails(User user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRole(){
        if(this.user.getRole() == UserRole.ROLE_USER){
            return "ROLE_USER";
        }
        else if (this.user.getRole()== UserRole.ROLE_ADMIN){
            return "ROLE_ADMIN";
        }
        else return "Undefined";
    }

    public int getId(){
        return this.user.getId();
    }


}
