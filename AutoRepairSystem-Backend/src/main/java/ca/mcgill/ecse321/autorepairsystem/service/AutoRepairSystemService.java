package ca.mcgill.ecse321.autorepairsystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autorepairsystem.dao.*;
import ca.mcgill.ecse321.autorepairsystem.model.*;

@Service
public class AutoRepairSystemService {

	@Autowired
	AdministratorRepository administratorRepository;
	@Autowired
	AppointmentRepository appointmentRepository;
	@Autowired
	BusinessHourRepository businessHourRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	TechnicianHourRepository technicanHourRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	WorkBreakRepository workBreakRepository;
	@Autowired
	WorkHourRepository workHourRepository;

	//Log-in generic user...either customer, technician, or admin
	@Transactional
	public User signIn(String userID, String password) throws IllegalArgumentException {
		User user = userRepository.findById(userID).orElse(null);
		if(user == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		} else if(user.getPassword().equals(password) == false) {
			throw new IllegalArgumentException("Password is wrong!.");
		}
		
		return user;
	}
	

	
	
}
