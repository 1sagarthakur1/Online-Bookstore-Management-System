package com.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bookstore.cotroller.UserController;
import com.bookstore.model.Users;
import com.bookstore.service.UserService;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        Users user = new Users(1, "john_doe", "password123", "john@example.com", "USER");

        // Mocking the encoding of the password
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword123");
        user.setPassword("encodedPassword123");
        user.setRole("ROLE_USER");

        // Mocking the service method
        when(userService.registerUser(user)).thenReturn(user);

        ResponseEntity<Users> response = userController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).registerUser(user);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void testGetUserById() {
        int userId = 1;
        Users user = new Users(userId, "john_doe", "encodedPassword123", "john@example.com", "ROLE_USER");

        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<Users> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testUpdateAdmin() {
        Users user = new Users(1, "admin_user", "encodedPassword123", "admin@example.com", "ROLE_ADMIN");

        when(userService.updateAdmin(user)).thenReturn(user);

        ResponseEntity<Users> response = userController.updateAdmin(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateAdmin(user);
    }

    @Test
    void testUpdateUser() {
        Users user = new Users(1, "john_doe", "encodedPassword123", "john@example.com", "ROLE_USER");

        when(userService.updateUser(user)).thenReturn(user);

        ResponseEntity<Users> response = userController.updateUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateUser(user);
    }
}

