package com.practice.crudBasicAPI.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.crudBasicAPI.dto.PatchEmailDTO;
import com.practice.crudBasicAPI.dto.UserCredentialsDTO;
import com.practice.crudBasicAPI.dto.UserDetailsDTO;
import com.practice.crudBasicAPI.dto.UserRegDTO;
import com.practice.crudBasicAPI.entity.Users;
import com.practice.crudBasicAPI.exceptionhandler.ConflictException;
import com.practice.crudBasicAPI.exceptionhandler.ResourceNotFoundException;
import com.practice.crudBasicAPI.repository.UserRepository;
import com.practice.crudBasicAPI.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.json.JsonPatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDetailsDTO> getAllUsers() {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        List<UserDetailsDTO> allUsers = new ArrayList<>();
        List<Users> users = new ArrayList<>(userRepository.findAll());
        for (Users u : users) {
            userDetailsDTO = modelMapper.map(u, UserDetailsDTO.class);
            allUsers.add(userDetailsDTO);
        }
        return allUsers;
    }

    @Override
    public List<UserDetailsDTO> getActiveUsers() {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        List<UserDetailsDTO> allActiveUsers = new ArrayList<>();
        List<Users> activeUsers = new ArrayList<>(userRepository.getActiveUsers());
        for (Users u : activeUsers) {
            userDetailsDTO = modelMapper.map(u, UserDetailsDTO.class);
            allActiveUsers.add(userDetailsDTO);
        }
        return allActiveUsers;
    }

    @Override
    public UserDetailsDTO getUserById(int id) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        Optional<Users> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()) {
            userDetailsDTO = modelMapper.map(existingUser.get(), UserDetailsDTO.class);
            return userDetailsDTO;
        }
        throw new ResourceNotFoundException("No existing user with id: " + id + " found");
    }

    @Override
    public UserDetailsDTO loginUser(UserCredentialsDTO userCred) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        Optional<Users> existingUser = userRepository.checkUserCred(userCred.getEmail(), userCred.getPassword());
        if(existingUser.isPresent()) {
            userDetailsDTO = modelMapper.map(existingUser.get(), UserDetailsDTO.class);
            return userDetailsDTO;
        }
        throw new ResourceNotFoundException("Email or password is incorrect");
    }

    @Override
    public UserDetailsDTO registerUser(UserRegDTO userDetails) {
        Optional<Users> existingUser = userRepository.findByEmail(userDetails.getEmail());
        if(existingUser.isPresent()) {
            throw new ConflictException("Email already exists!");
        }
        Users u = new Users();
        u.setEmail(userDetails.getEmail());
        u.setPassword(userDetails.getPassword());
        u.setFname(userDetails.getFname());
        u.setLname(userDetails.getLname());
        var userReg = userRepository.save(u);
        return modelMapper.map(userReg, UserDetailsDTO.class);
    }

    @Override
    public UserDetailsDTO updateUser(int id, UserRegDTO udUser) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        Optional<Users> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()){
            Users userDetails = existingUser.get();
            userDetails.setEmail(udUser.getEmail());
            userDetails.setPassword(udUser.getPassword());
            userDetails.setFname(udUser.getFname());
            userDetails.setLname(udUser.getLname());
            userRepository.save(userDetails);
            userDetailsDTO = modelMapper.map(userDetails, UserDetailsDTO.class);
            return userDetailsDTO;
        }
        throw new ResourceNotFoundException("No existing user with id: " + id + " found");
    }

//---------------------------------Patching---------------------------------

//    public Users applyPatch(JsonPatch patch, Users user) throws JsonPatchException,
//            JsonProcessingException {
//
//    }

//    @Override
//    public UserDetailsDTO patchUserEmail(int id, PatchEmailDTO patchEmailDTO) {
//        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
//        Optional<Users> existingUser = userRepository.findById(id);
//        if (existingUser.isPresent()){
//            Users userDetails = existingUser.get();
//            userDetails.setEmail(patchEmailDTO.getEmail());
//            userRepository.save(userDetails);
//            userDetailsDTO = modelMapper.map(userDetails, UserDetailsDTO.class);
//            return userDetailsDTO;
//        }
//        throw new ResourceNotFoundException("No existing user with id: " + id + " found");
//    }

    @Override
    public UserDetailsDTO softDeleteUser(int id) {
        userRepository.softDeleteUser(id);
        return getUserById(id);
    }
}
