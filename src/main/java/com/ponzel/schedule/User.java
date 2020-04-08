package com.ponzel.schedule;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    @NotBlank(message = "Enter username")
    private String username;
    @NotBlank
    private String password;
    @NotBlank(message = "Enter your First name")
    private String firstName;
    @NotBlank(message = "Enter your Last name")
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;
    private RoleOfUser role;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules;

    public static enum RoleOfUser{
        ROLE_USER,
        ROLE_ADMIN
    }
    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber, RoleOfUser role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;

    }
    public boolean isAdmin(){
        if(role == RoleOfUser.ROLE_ADMIN) return true;
        return false;
    }

    public String getFirstAndLastName(){
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  Arrays.asList(new SimpleGrantedAuthority(role.toString()));
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
}
