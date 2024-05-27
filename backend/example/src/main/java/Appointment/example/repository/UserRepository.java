package Appointment.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Appointment.example.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByUserName(String username);
}
