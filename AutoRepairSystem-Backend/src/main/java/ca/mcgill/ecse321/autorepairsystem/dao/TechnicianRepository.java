package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Technician;

public interface TechnicianRepository extends CrudRepository<Technician, String>{
	Technician findTechnicianByUsername(String username);

}