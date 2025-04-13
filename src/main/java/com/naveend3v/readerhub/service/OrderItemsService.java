package com.naveend3v.readerhub.service;

import com.naveend3v.readerhub.model.OrderItems;
import com.naveend3v.readerhub.repository.OrderItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderItemsService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public void addOrderedBooks(OrderItems orderItem){
        orderItemsRepository.save(orderItem);
    }
}
