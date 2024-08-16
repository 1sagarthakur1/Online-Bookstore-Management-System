package com.bookstore.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Orders;
import com.bookstore.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //Handle by Admin
    @GetMapping("/admin/getOrdersByUserId/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@Valid @PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    //Handle by Admin
    @GetMapping("/admin/getOrderById/{id}")
    public ResponseEntity<Orders> getOrderById(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    //Handle by User
    @PostMapping("/user/placeOrder/{bookId}")
    public ResponseEntity<Orders> placeOrder(@Valid @PathVariable("bookId") Integer bookId) {
        return new ResponseEntity<>(orderService.placeOrder(bookId), HttpStatus.CREATED);
    }

    //Handle by Admin
    @PutMapping("/admin/updateOrderStatus/{id}/{status}")
    public ResponseEntity<String> updateOrderStatus(@Valid @PathVariable Integer id, @PathVariable("status") String status) {
        return new ResponseEntity<String>(orderService.updateOrderStatus(id, status), HttpStatus.OK);
    }
}
