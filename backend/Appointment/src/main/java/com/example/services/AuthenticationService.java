package com.example.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.model.User;
import com.example.repository.UserRepository;

public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(UserRepository userRepository,AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,JwtService jwtService) {
		this.jwtService=jwtService;
		this.passwordEncoder=passwordEncoder;
		this.userRepository=userRepository;
		this.authenticationManager=authenticationManager;
	}
	
	public String register(User request) {
		User user=new User();
		user.setUserName(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user=userRepository.save(user);
		String token=jwtService.generateToken(user);
		return token;
		
	}
	
	public String authenticate(User request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		User user=userRepository.findbyUserName(request.getUsername()).orElseThrow();
		String token=jwtService.generateToken(user);
		return token;
	}

}
