package com.naveend3v.readerhub.service;

import com.naveend3v.readerhub.dto.userdetails.UserDetailsDto;
import com.naveend3v.readerhub.model.UserDetails;
import com.naveend3v.readerhub.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<UserDetailsDto> getAllUsers() {
        List<UserDetailsDto> getAllUsers =  userDetailsRepository.findAll()
                .stream()
                .map(user -> new UserDetailsDto(
                        user.getId(),
                        user.getName(),
                        user.getRoles(),
                        user.getEmail()
                ))
                .toList();
        return getAllUsers;
    }

    public Optional<UserDetailsDto> findByName(String username) {
        return userDetailsRepository.findByName(username);
    }

    public String saveUser(UserDetails userDetails) {
        String newUsername = userDetails.getName();
        String updatedUsername = userDetailsRepository.save(userDetails).getName();
        try{
        if(newUsername.equals(updatedUsername)){
            return "User updated successfully";
        }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "Error occurred while updating user";
    }

    public Optional<UserDetailsDto> findById(Integer userId) {
        try {
            Optional<UserDetailsDto> getUser = userDetailsRepository.findById(userId)
                    .stream()
                    .map((user) -> new UserDetailsDto(
                            user.getId(),
                            user.getName(),
                            user.getEmail())).findFirst();
            if(getUser.isPresent()){
                return getUser;
            }
        } catch (RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

}
