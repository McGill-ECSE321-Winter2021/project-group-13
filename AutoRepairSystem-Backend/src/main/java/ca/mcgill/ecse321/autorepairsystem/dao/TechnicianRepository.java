package ca.mcgill.ecse321.autorepairsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;

public interface TechnicianRepository extends CrudRepository<Technician, String>{
	Technician findTechnicianByUsername(String username);
}