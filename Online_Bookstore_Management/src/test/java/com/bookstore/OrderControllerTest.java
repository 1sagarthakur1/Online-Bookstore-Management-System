package com.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bookstore.cotroller.OrderController;
import com.bookstore.model.Book;
import com.bookstore.model.Orders;
import com.bookstore.model.Users;
import com.bookstore.service.OrderService;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersByUserId() {
        int userId = 1;
        Users user = new Users(); // create a user object with appropriate fields
        Orders order1 = new Orders(1, 100, "Processing", user, new Book());
        Orders order2 = new Orders(2, 150, "Shipped", user, new Book());
        List<Orders> ordersList = Arrays.asList(order1, order2);

        when(orderService.getOrdersByUserId(userId)).thenReturn(ordersList);

        ResponseEntity<List<Orders>> response = orderController.getOrdersByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ordersList, response.getBody());
        verify(orderService, times(1)).getOrdersByUserId(userId);
    }

    @Test
    void testGetOrderById() {
        int orderId = 1;
        Orders order = new Orders(orderId, 100, "Processing", new Users(), new Book());

        when(orderService.getOrderById(orderId)).thenReturn(order);

        ResponseEntity<Orders> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void testPlaceOrder() {
        int bookId = 1;
        Orders order = new Orders(1, 100, "Processing", new Users(), new Book());

        when(orderService.placeOrder(bookId)).thenReturn(order);

        ResponseEntity<Orders> response = orderController.placeOrder(bookId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).placeOrder(bookId);
    }

    @Test
    void testUpdateOrderStatus() {
        int orderId = 1;
        String status = "Shipped";

        when(orderService.updateOrderStatus(orderId, status)).thenReturn("Order status updated successfully");

        ResponseEntity<String> response = orderController.updateOrderStatus(orderId, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order status updated successfully", response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, status);
    }
}

