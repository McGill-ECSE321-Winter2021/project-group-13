package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;

public interface TechnicianHourRepository extends CrudRepository<TechnicianHour, String>{

	TechnicianHour findTechnicianHourById (Integer id);
	List<TechnicianHour> findByDate(java.sql.Date date);
}