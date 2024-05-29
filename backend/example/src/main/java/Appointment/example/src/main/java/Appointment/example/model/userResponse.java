package Appointment.example.model;

import java.util.List;

public class userResponse {
	
	public Boolean status;
	public List<String> users;
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}

}
