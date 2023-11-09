package com.practice.crudBasicAPI.controller;

import com.practice.crudBasicAPI.dto.*;
import com.practice.crudBasicAPI.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;

    public UserController(IUserService iUserService) {
        super();
        this.iUserService = iUserService;
    }
    @GetMapping("/getUsers") //[WORKING]
    public ResponseEntity<List<UserDetailsDTO>> getAllUsers(){
        List<UserDetailsDTO> users = iUserService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
//    @GetMapping("/getActiveUsers") //[WORKING]
//    public ResponseEntity<List<UserDetailsDTO>> getActiveProducts(){
//        List<UserDetailsDTO> users = iUserService.getActiveUsers();
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
    @GetMapping("/getUser/{id}") //[WORKING]
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable int id){
        UserDetailsDTO user = iUserService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/getUserByEmail/{email}") //[WORKING]
    public ResponseEntity<UserDetailsDTO> getUserByEmail(@PathVariable String email){
        UserDetailsDTO user = iUserService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/login") //[WORKING]
    public ResponseEntity<UserDetailsDTO> loginUser(@RequestBody UserCredentialsDTO userCred){
        UserDetailsDTO user = iUserService.loginUser(userCred);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/register") //[WORKING]
    public ResponseEntity<UserDetailsDTO> addNewUser(@RequestBody UserRegDTO user){
        return new ResponseEntity<UserDetailsDTO>(iUserService.registerUser(user), HttpStatus.CREATED);
    }
    @PutMapping("/updateUser/{id}") //[WORKING]
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable Integer id, @RequestBody UserRegDTO udUser){
        UserDetailsDTO update_user = iUserService.updateUser(id, udUser);
        return new ResponseEntity<>(update_user, HttpStatus.OK);
    }
//    @PatchMapping("/patchUserEmail/{id}") //[WORKING]
//    public ResponseEntity<UserDetailsDTO> patchUserEmail(@PathVariable Integer id, @RequestBody PatchEmailDTO pEmail){
//        UserDetailsDTO patch_email = iUserService.patchUserEmail(id, pEmail);
//        return new ResponseEntity<>(patch_email, HttpStatus.OK);
//    }
    @PutMapping("/softDeleteUser/{id}") //[WORKING]
    public ResponseEntity<UserDetailsDTO> softDeleteUser(@PathVariable Integer id){
        return new ResponseEntity<>(iUserService.softDeleteUser(id), HttpStatus.OK);
    }

    @DeleteMapping ("/hardDeleteUser/{id}") //[WORKING]
    public ResponseEntity<UserDetailsDTO> hardDeleteUser(@PathVariable Integer id){
        iUserService.hardDeleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
