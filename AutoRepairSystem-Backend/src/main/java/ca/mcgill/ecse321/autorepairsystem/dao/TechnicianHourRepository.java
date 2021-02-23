package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;

public interface TechnicianHourRepository extends CrudRepository<TechnicianHour, String>{
	TechnicianHour findTechnicianHourByTechnician (Technician tech);
	TechnicianHour findTechnicianHourById (Integer id);
}