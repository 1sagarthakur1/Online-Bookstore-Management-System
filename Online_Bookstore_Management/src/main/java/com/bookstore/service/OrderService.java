package com.bookstore.service;

import java.util.List;

import com.bookstore.exception.OrderException;
import com.bookstore.model.Orders;

public interface OrderService {
    public List<Orders> getOrdersByUserId(Integer userId) throws OrderException;
    public Orders getOrderById(Integer id) throws OrderException;
    public Orders placeOrder( Integer bookId) throws OrderException;
    public String updateOrderStatus(Integer id, String status) throws OrderException;
}
