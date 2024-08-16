package com.bookstore.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Users;
import com.bookstore.repository.UserRepository;

@RestController
//@RequestMapping(value="/api")
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/signIn")
	public ResponseEntity<Users> getLoggedInUserDetailsHandler(Authentication auth){
		
		 Users user= userRepository.findByEmail(auth.getName()).orElseThrow(() -> new BadCredentialsException("Invalid Username or password")); 
		 return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
		 
	}
	
}
