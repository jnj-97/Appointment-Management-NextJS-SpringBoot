package com.example.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.AppManage;
import com.example.services.AppManageService;

import jakarta.validation.Valid;

@Controller
public class AppManagementController {

	@Autowired
	private AppManageService appointment;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
	}
	
	@RequestMapping(value="/update-appointment",method=RequestMethod.POST)
	public String updateAppointment(ModelMap model,@Valid AppManage appoint, BindingResult result) {
		if(result.hasErrors()) {
			return "{status:false,message:'Internal server error'}";
		}
		appoint.setUserName("Hello");
		appointment.updateAppointment(appoint);
		return "redirect:/list-appoint";
		
	}
	
	@RequestMapping(value="/add-appointment",method=RequestMethod.POST)
	public String addAppointment(ModelMap model,@Valid AppManage appoint, BindingResult result) {
		if(result.hasErrors()) {
			return "{status:false,message:'Internal server error'}";
		}
		appoint.setUserName("Hello");
		appointment.updateAppointment(appoint);
		return "redirect:/list-appoint";
		
	}
	
}
