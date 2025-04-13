package com.naveend3v.readerhub.repository;

import com.naveend3v.readerhub.model.Cart;
import com.naveend3v.readerhub.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findAllByUserInfo(UserInfo userInfo);
    void deleteByUserInfo(UserInfo userInfo);
}
