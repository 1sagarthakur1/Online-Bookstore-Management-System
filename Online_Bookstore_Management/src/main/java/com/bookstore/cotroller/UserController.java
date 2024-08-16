package com.bookstore.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Users;
import com.bookstore.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@Valid @RequestBody Users user) {
    	
        user.setRole("ROLE_"+user.getRole().toUpperCase());	
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

   //Handle by Admin
    @GetMapping("/admin/getUserById/{id}")
    public ResponseEntity<Users> getUserById(@Valid @PathVariable Integer id) {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
  //Handle by Admin
    @PutMapping("/admin/updateUser")
    public ResponseEntity<Users> updateAdmin(@Valid @RequestBody Users user) {
        Users updatedUser = userService.updateAdmin(user);
        return ResponseEntity.ok(updatedUser);
    }

  //Handle by User
    @PutMapping("/user/updateUser")
    public ResponseEntity<Users> updateUser(@Valid @RequestBody Users user) {
        Users updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

  //Handle by Admin or User
//    @DeleteMapping("/disableUser/{id}")
//    public ResponseEntity<Void> disableUser(@Valid @PathVariable Integer id) {
//        userService.disableUser(id);
//        return ResponseEntity.noContent().build();
//    }

    // Get current authenticated user
//    @GetMapping("/me")
//    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userService.getUserById(Long.valueOf(userDetails.getUsername())); // Assuming username is ID
//        return ResponseEntity.ok(user);
//    }
}
