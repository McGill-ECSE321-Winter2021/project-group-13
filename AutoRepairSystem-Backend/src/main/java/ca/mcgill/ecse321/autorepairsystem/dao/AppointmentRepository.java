package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Service;

public interface AppointmentRepository extends CrudRepository<Appointment, String>{

	List<Appointment> findByCustomer(Customer username);
	
	List<Appointment> findByTechnician(Customer username);
	
	boolean existsByCustomerAndService(Customer username, Service serviceName);
	
	List<Appointment> findByCustomerAndService(Customer username, Service serviceName);
	
	Appointment findByCustomerAndStartTime(Customer username, Appointment startTime);
}