package com.bookstore.service;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bookstore.exception.UserException;
import com.bookstore.model.Users;
import com.bookstore.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

//	    @Autowired
//	    private PasswordEncoder passwordEncoder;

	@Override
	public Users registerUser(Users user) throws UserException {
		Optional<Users> user2 = userRepository.findByEmail(user.getEmail());
		if (user2.isPresent()) {
			throw new UserException("User all ready exist");
		}
		return userRepository.save(user);
	}

	@Override
	public Users getUserById(Integer id) throws UserException {
		return userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
	}

	@Override
	public Users updateUser(Users user) throws UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Users> users = userRepository.findByEmail(authentication.getName());
		if(users.isEmpty() || users.get().getRole().equals("ROLE_ADMIN")) {
			throw new UserException("Please login as user");
		}
		users.get().setUsername(user.getUsername());
		return userRepository.save(users.get());
	}
	
	@Override
	public Users updateAdmin(Users user) throws UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Users> users = userRepository.findByEmail(authentication.getName());
		if(users.isEmpty() || users.get().getRole().equals("ROLE_USER")) {
			throw new UserException("Please login as user");
		}
		users.get().setUsername(user.getUsername());
		return userRepository.save(users.get());
	}

	@Override
	public void disableUser(Integer id) throws UserException {
		Users user = getUserById(id);
		userRepository.save(user);
	}
}
