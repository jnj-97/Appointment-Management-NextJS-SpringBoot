package Appointment.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Appointment.example.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting,Long> {

	Optional<Meeting> findById(long id);

    List<Meeting> findByOrganizedBy(String organizedBy);

    List<Meeting> findByOrganizedFor(String organizedFor);
    
    
    
}
