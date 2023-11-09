package com.practice.crudBasicAPI.service;

import com.practice.crudBasicAPI.dto.PatchEmailDTO;
import com.practice.crudBasicAPI.dto.UserCredentialsDTO;
import com.practice.crudBasicAPI.dto.UserDetailsDTO;
import com.practice.crudBasicAPI.dto.UserRegDTO;

import java.util.List;

public interface IUserService {
    List<UserDetailsDTO> getAllUsers();
    List<UserDetailsDTO> getActiveUsers();
    UserDetailsDTO getUserById(int id);
    UserDetailsDTO loginUser(UserCredentialsDTO userCred);
    UserDetailsDTO registerUser(UserRegDTO userDetails);
    UserDetailsDTO updateUser(int id, UserRegDTO udUser);
//    UserDetailsDTO patchUserEmail(int id, PatchEmailDTO patchEmailDTO);
    UserDetailsDTO softDeleteUser(int id);
    void logoutUser(String email);
    UserDetailsDTO checkIsLoggedIn();

    //Hard Delete User
    void hardDeleteUser(int id);
}
