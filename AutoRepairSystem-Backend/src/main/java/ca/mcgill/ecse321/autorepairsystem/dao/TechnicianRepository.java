package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;

public interface TechnicianRepository extends CrudRepository<Technician, String>{
	Technician findTechnicianByUsername(String username);
	//Technician findTechnicianByTechnicianHour(TechnicianHour technicianHour);

}