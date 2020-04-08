package com.ponzel.schedule.security;

import com.ponzel.schedule.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Data
public class RegistrationForm {
    @NotBlank(message = "Enter username")
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private User.RoleOfUser role;


    public User toUser(PasswordEncoder passwordEncoder){
      if(username.equalsIgnoreCase("admin")){
          role = User.RoleOfUser.ROLE_ADMIN;
      }else {
          role = User.RoleOfUser.ROLE_USER;
      }
        return new User(username, passwordEncoder.encode(password), firstName, lastName, email, phoneNumber, role);
    }

}
