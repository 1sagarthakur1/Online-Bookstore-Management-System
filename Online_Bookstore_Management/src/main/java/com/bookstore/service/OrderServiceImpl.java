package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bookstore.exception.OrderException;
import com.bookstore.exception.UserException;
import com.bookstore.model.Book;
import com.bookstore.model.Orders;
import com.bookstore.model.Users;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Orders> getOrdersByUserId(Integer userId) throws OrderException{
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Orders getOrderById(Integer id) throws OrderException{
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Order not found with id: " + id));
    }

    @Override
    public Orders placeOrder(Integer bookId) throws OrderException {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Users users = userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new UserException("Please Login First"));
		
        Optional<Users> userOptional = userRepository.findById(users.getId());
        if (userOptional.isEmpty()) {
            throw new UserException("This user is not present");
        }
        
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new UserException("This book is not present");
        }
        
        Orders order = new Orders();
        
        order.setPrice(bookOptional.get().getPrice());
        order.setUser(userOptional.get());
        order.setBooks(bookOptional.get());
        order.setStatus("Ordered");

        return orderRepository.save(order);
    }

    @Override
    public String updateOrderStatus(Integer id, String status) throws OrderException{
        Orders order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
        
        return "Status Changed";
    }
}
