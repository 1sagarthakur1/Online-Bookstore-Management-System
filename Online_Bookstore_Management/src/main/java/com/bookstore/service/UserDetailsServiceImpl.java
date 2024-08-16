package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.model.Users;
import com.bookstore.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository usersRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String usersname) throws UsernameNotFoundException {
		
		
		Optional<Users> opt= usersRepository.findByEmail(usersname);

		if(opt.isPresent()) {
			
			Users users= opt.get();
			
			List<GrantedAuthority> authorities= new ArrayList<>();
			SimpleGrantedAuthority sga= new SimpleGrantedAuthority(users.getRole());
			authorities.add(sga);
			
			return new User(users.getEmail(), users.getPassword(), authorities);
			
		}else
			throw new BadCredentialsException("Users Details not found with this usersname: "+usersname);
		
		
	}

}
