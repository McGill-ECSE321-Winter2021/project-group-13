package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Service;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;

public interface AppointmentRepository extends CrudRepository<Appointment, String>{
	
    //Can't remove these two because used in tests
	Appointment findAppointmentByCustomer (Customer customer);
	Appointment findAppointmentByTechnician (Technician technician);
	
	Appointment findAppointmentById (Integer id);

	List<Appointment> findByCustomer(Customer username);
	
	//I modified this
	List<Appointment> findByTechnician(Technician technician);
	
	boolean existsByCustomerAndService(Customer username, Service serviceName);
	
	List<Appointment> findByCustomerAndService(Customer username, Service serviceName);
	
	Appointment findByCustomerAndStartTime(Customer username, Appointment startTime);
	
	//I added this
	List<Appointment> findByDate(java.sql.Date date);
}