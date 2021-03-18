package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.Set;
import java.sql.Date;

import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;

public interface AppointmentRepository extends CrudRepository<Appointment, String>{
	
    //Can't remove these two because used in tests
	Set<Appointment> findAppointmentByCustomer (Customer customer);
	Set<Appointment> findAppointmentByTechnician (Technician technician);
	Set<Appointment> findAppointmentByDate(Date date);
	
	Appointment findAppointmentById (Integer id);
}