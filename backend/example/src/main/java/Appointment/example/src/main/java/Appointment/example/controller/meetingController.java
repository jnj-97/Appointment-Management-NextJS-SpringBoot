package Appointment.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import Appointment.example.model.Meeting;
import Appointment.example.model.meetingResponse;
import Appointment.example.repository.MeetingRepository;
import Appointment.example.service.JwtService;

@RestController
public class meetingController {

	private final JwtService jwtService;
	private final MeetingRepository meetingRepository;
	
	public meetingController(JwtService jwtService, MeetingRepository meetingRepository) {
		this.jwtService=jwtService;
		this.meetingRepository=meetingRepository;
	}
	
	@GetMapping("/meeting-list")
	public meetingResponse meetingList(@RequestHeader("auth-header") String authHeader){
		meetingResponse meetingresponse=new meetingResponse();
		if(jwtService.isValid(authHeader)) {
			String username=jwtService.extractUsername(authHeader);
			List<Meeting> meeting_for=meetingRepository.findByOrganizedFor(username);
			List<Meeting> meeting_by=meetingRepository.findByOrganizedBy(username);
			Set<Meeting> uniqueMeetings = new HashSet<>(meeting_for);
		    uniqueMeetings.addAll(meeting_by);
		    
		    List<Meeting> unique= uniqueMeetings.stream().collect(Collectors.toList());
		    meetingresponse.setStatus(true);
		    meetingresponse.setMeeting(unique);
			return meetingresponse;
		}
		else {
			meetingresponse.setStatus(false);
		    return meetingresponse;
			
		}
		
	}
	
	@PostMapping("/create-meeting")
	public meetingResponse createMeeting(@RequestHeader("auth-header") String authHeader,@RequestBody Date date,@RequestBody String organised_for,@RequestBody String description) {
		meetingResponse meetingresponse=new meetingResponse();
		if(jwtService.isValid(authHeader)) {
			String username=jwtService.extractUsername(authHeader);
			Meeting meeting=new Meeting();
			meeting.setDate(date);
			meeting.setOrganizedBy(username);
			meeting.setOrganizedFor(organised_for);
			meeting.setDescription(description);
			meetingRepository.save(meeting);
			meetingresponse.setStatus(true);
			return meetingresponse;
		}
		else {
			meetingresponse.setStatus(false);
			return meetingresponse;
		}
	}
	
	@PostMapping("/update-meeting")
	public meetingResponse updateMeeting(@RequestHeader("auth-header") String authHeader,@RequestBody Date date,@RequestBody String organised_for,@RequestBody String description,@RequestBody long id) {
		meetingResponse meetingresponse=new meetingResponse();
		if(jwtService.isValid(authHeader)) {
			String username=jwtService.extractUsername(authHeader);
			meetingRepository.findById(id)
            .map(existingMeeting -> {
                existingMeeting.setDate(date);
                existingMeeting.setOrganizedBy(username);
                existingMeeting.setOrganizedFor(organised_for);
                existingMeeting.setDescription(description);
                return meetingRepository.save(existingMeeting);
            });
			meetingresponse.setStatus(true);
			return meetingresponse;
		}
		else {
			meetingresponse.setStatus(false);
			return meetingresponse;
		}
	}
	
	@DeleteMapping("/delete-meeting")
	public meetingResponse deleteMeeting(@RequestHeader("auth-header") String authHeader,@RequestBody long id) {
		meetingResponse meetingresponse=new meetingResponse();
		if(jwtService.isValid(authHeader)) {
		meetingRepository.deleteById(id);
		meetingresponse.setStatus(true);
		return meetingresponse;
		}
		else {
			meetingresponse.setStatus(false);
			return meetingresponse;
		}
	}
	
}
