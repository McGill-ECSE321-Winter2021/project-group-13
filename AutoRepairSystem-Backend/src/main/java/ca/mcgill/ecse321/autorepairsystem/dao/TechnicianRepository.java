package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;

public interface TechnicianRepository extends CrudRepository<Technician, String>{
	Technician findTechnicianByUsername(String username);
	Technician findTechnicianByAppointment(Appointment appointment);
	Technician findTechnicianByTechnicianHour(TechnicianHour technicianHour);

}