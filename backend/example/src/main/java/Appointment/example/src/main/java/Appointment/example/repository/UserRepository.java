package Appointment.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Appointment.example.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByUserName(String username);
	
	@Query("SELECT u.userName FROM User u")
    List<String> findAllUserNames();
}
