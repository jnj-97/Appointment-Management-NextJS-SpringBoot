package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.AppManage;

public interface AppointmentRepository extends JpaRepository<AppManage,Long> {
	List<AppManage> findByUserName(String user);

}
