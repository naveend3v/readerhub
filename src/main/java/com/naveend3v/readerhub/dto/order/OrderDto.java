package com.naveend3v.readerhub.dto.order;

import com.naveend3v.readerhub.model.Orders;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {
    private int orderId;
    private String orderDate;
    private double totalAmount;
    private List<OrderItemsDto> orderItems;

    public OrderDto(Orders order) {
        this.orderId = order.getId();
        this.orderDate = order.getCreatedDate().toString();
        this.totalAmount = order.getTotalPrice();

        // Convert OrderItems list to OrderItemDto list
        this.orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemsDto::new)
                .collect(Collectors.toList());
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemsDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsDto> orderItems) {
        this.orderItems = orderItems;
    }
}
