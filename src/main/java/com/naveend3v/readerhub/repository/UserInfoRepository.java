package com.naveend3v.readerhub.repository;

import com.naveend3v.readerhub.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    List<UserInfo> findAll();
    Optional<UserInfo> findByName(String name);
    Optional<UserInfo> findById(Integer userId);
    UserInfo save(UserInfo userInfo);
}
