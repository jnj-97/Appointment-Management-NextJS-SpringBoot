package com.example.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.model.AppManage;

public interface AppManageService {
	List<AppManage> getAppointmentByUser(String user);
	Optional<AppManage> getAppointmentById(long id);
	void updateAppointment(AppManage appointment);
	void addAppointment(String userName,String desc, Date targetDate,boolean isDone );
	void deleteAppointment(long id);
	void saveAppointment(AppManage appointment);
	

}
