package com.practice.crudBasicAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private int id;
    private String email;
    private String password;
    private String fullname;
    private String phoneNumber;
//    private boolean isLoggedIn;
    private LocalDateTime date_registered;
}
