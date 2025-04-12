package com.naveend3v.readerhub.repository;

import com.naveend3v.readerhub.dto.userdetails.UserDetailsDto;
import com.naveend3v.readerhub.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer> {
    List<UserDetails> findAll();
    Optional<UserDetailsDto> findByName(String name);
    Optional<UserDetails> findById(Integer userId);
    UserDetails save(UserDetails userDetails);
}
