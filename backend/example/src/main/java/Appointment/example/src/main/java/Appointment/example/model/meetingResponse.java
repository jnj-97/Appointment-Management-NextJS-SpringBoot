package Appointment.example.model;

import java.util.List;

public class meetingResponse {

	private Boolean status;
	private List<Meeting> meetings;
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<Meeting> getMeeting() {
		return meetings;
	}
	public void setMeeting(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
}
