package Appointment.example.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="meeting")
public class Meeting {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="time")
	private Date date;
	
	@Column(name="organized_by")
	private String organizedBy;
	
	@Column(name="organized_for")
	private String organizedFor;
	
	public String getOrganizedBy() {
		return organizedBy;
	}

	public void setOrganizedBy(String organizedBy) {
		this.organizedBy = organizedBy;
	}

	public String getOrganizedFor() {
		return organizedFor;
	}

	public void setOrganizedFor(String organizedFor) {
		this.organizedFor = organizedFor;
	}

	@Column(name="description")
	private String description;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
