package com.naveend3v.readerhub.dto.order;

import com.naveend3v.readerhub.model.OrderItems;
import com.naveend3v.readerhub.model.Orders;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GetOrderDetailsDto {

    private Integer orderId;
    private @NotNull Integer userId;
    private Integer totalPrice;
    private List<OrderItems> orderItems;

    public GetOrderDetailsDto(){}

    public GetOrderDetailsDto(Orders orders){
        this.orderId = orders.getId();
        this.userId = orders.getUserId();
        this.orderItems = orders.getOrderItems();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }
}
