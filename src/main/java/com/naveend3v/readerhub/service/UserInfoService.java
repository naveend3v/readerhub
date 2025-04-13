package com.naveend3v.readerhub.service;

import com.naveend3v.readerhub.dto.userdetails.UserDetailsDto;
import com.naveend3v.readerhub.model.UserInfo;
import com.naveend3v.readerhub.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public List<UserDetailsDto> getAllUsers() {
        List<UserDetailsDto> getAllUsers =  userInfoRepository.findAll()
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

    public Optional<UserInfo> findByName(String username) {
        return userInfoRepository.findByName(username);
    }

    public String saveUser(UserInfo userInfo) {
        String newUsername = userInfo.getName();
        String updatedUsername = userInfoRepository.save(userInfo).getName();
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
            Optional<UserDetailsDto> getUser = userInfoRepository.findById(userId)
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
