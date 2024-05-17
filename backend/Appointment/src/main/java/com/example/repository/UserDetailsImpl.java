package com.example.repository;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class UserDetailsImpl implements UserDetailsService {

	private final UserRepository repository;
	
	public UserDetailsImpl(UserRepository userRepo) {
		this.repository=userRepo;
		
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repository.findbyUserName(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
	}

}
