package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.sql.Time;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;

public interface WorkBreakRepository extends CrudRepository<WorkBreak, String>{
	WorkBreak findWorkBreakByStartBreak(Time startbreak);
}