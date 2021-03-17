package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.WorkHour;

public interface WorkHourRepository extends CrudRepository<WorkHour, String>{
	WorkHour findWorkHourById(Integer id);
	WorkHour findWorkHourByWorkBreak(WorkBreak workBreak);
}