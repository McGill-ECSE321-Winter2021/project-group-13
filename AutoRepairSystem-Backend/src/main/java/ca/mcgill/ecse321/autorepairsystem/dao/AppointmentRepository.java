package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.Set;
import java.sql.Date;

import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.WorkItem;

public interface AppointmentRepository extends CrudRepository<Appointment, String>{
	
	Set<Appointment> findAppointmentByCustomer (Customer customer);
	Set<Appointment> findAppointmentByTechnician (Technician technician);
	Set<Appointment> findAppointmentByDate(Date date);
	Set<Appointment> findAppointmentByWorkItem(WorkItem workItem);
	
	Appointment findAppointmentById (Integer id);
}