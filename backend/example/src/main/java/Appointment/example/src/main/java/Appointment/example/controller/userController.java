package Appointment.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import Appointment.example.model.userResponse;
import Appointment.example.repository.UserRepository;
import Appointment.example.service.JwtService;

@RestController
public class userController {

	private JwtService jwtService;
	private UserRepository userRepository;
	
	public userController(JwtService jwtService,UserRepository userRepository) {
		this.jwtService=jwtService;
		this.userRepository=userRepository;
	}
	
	@GetMapping("/user-list")
	public userResponse getUsers(@RequestHeader("auth-header") String authHeader) {
		userResponse userresponse=new userResponse();
		if(jwtService.isValid(authHeader)) {
			userresponse.setStatus(true);
			userresponse.setUsers(userRepository.findAllUserNames());
			return userresponse;
		}
		else {
			return userresponse;
		}
	}
}
