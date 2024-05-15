package com.example.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.AppManage;
import com.example.repository.AppointmentRepository;

@Service
public class CAppManageService implements AppManageService {
	
	@Autowired
	public AppointmentRepository appointmentRepository;
	
	@Override
	public List<AppManage> getAppointmentByUser(String user){
		return appointmentRepository.findByUserName(user);
	}
	
	@Override
	public Optional<AppManage> getAppointmentById(long id){
		return appointmentRepository.findById(id);
	}
	
	@Override
	public void updateAppointment(AppManage appointment){
		appointmentRepository.save(appointment);
	}
	
	@Override
	public void addAppointment(String userName,String desc, Date targetDate, boolean isDone){
		appointmentRepository.save(new AppManage(userName,desc,targetDate,isDone));
	}

	@Override
	public void deleteAppointment(long id){
		Optional<AppManage> appointment=appointmentRepository.findById(id);
		if(appointment.isPresent()) {
			appointmentRepository.delete(appointment.get());
		}
	}
	
	@Override
	public void saveAppointment(AppManage appointment){
		appointmentRepository.save(appointment);
	}
}
