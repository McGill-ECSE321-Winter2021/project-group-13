package ca.mcgill.ecse321.autorepairsystem.dao;

import java.sql.Time;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;

public interface WorkBreakRepository extends CrudRepository<WorkBreak, String>{
	WorkBreak findWorkBreakById(Integer Id);
	
}