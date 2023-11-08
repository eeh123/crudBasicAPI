package com.practice.crudBasicAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDTO {
    private String email;
    private String password;
//
//    public boolean getIsLoggedIn() {
//        return isLoggedIn;
//    }
//
    private boolean isLoggedIn;
}
