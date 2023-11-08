package com.practice.crudBasicAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegDTO {
    private String email;
    private String password;
    private String fullname;
    private String phoneNumber;
    private boolean isLoggedIn;
//    public boolean getIsLoggedIn() {
//        return isLoggedIn;
//    }
}
