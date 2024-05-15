package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.services.AppManageService;

public class AppManagementController {

	@Autowired
	private AppManageService appointment;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		
	}
}
