package Appointment.example.controller;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Appointment.example.model.User;
import Appointment.example.model.registerResponse;
import Appointment.example.model.tokenResponse;
import Appointment.example.repository.UserRepository;
import Appointment.example.service.JwtService;

@RestController
public class baseController {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public baseController(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.jwtService=jwtService;
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	@PostMapping("/register")
	public registerResponse register(@RequestBody User user) {
	    registerResponse response = new registerResponse();

	    if (user == null || user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
	        response.setStatus(false);
	    	response.setResponse("Invalid or Missing credentials!");
	        return response;
	    }

	    if (userRepository.findByUserName(user.getUsername()).isEmpty()) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	        response.setStatus(true);
	        response.setResponse("User successfully registered!");
	        return response;
	    } else {
	    	response.setStatus(false);
	        response.setResponse("User with Username Already Exists");
	        return response;
	    }
	}

	
	@PostMapping("/login")
	public tokenResponse login(@RequestBody User user) {
		tokenResponse response=new tokenResponse();
		if(user.getUsername()==null || user.getPassword()==null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
			response.setStatus(false);
			response.setToken("Invalid or missing credentials");
			return response;
		}
		Optional<User> registeredUser=userRepository.findByUserName(user.getUsername());
		if(registeredUser.isPresent()) {
			if(passwordEncoder.matches(user.getPassword(), registeredUser.get().getPassword())) {
			response.setStatus(true);
			response.setToken(jwtService.generateToken(user));
			return response;
			}
			else {
				response.setStatus(false);
				response.setToken("Invalid password");
				return response;
			}
		}
		else {
			response.setStatus(false);
			response.setToken("User doesn't exist");
			return response;
		}
			}
}
