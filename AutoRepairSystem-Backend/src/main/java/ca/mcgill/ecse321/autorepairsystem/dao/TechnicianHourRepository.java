package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Set;

import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;

public interface TechnicianHourRepository extends CrudRepository<TechnicianHour, String>{

	TechnicianHour findTechnicianHourById (Integer id);
	Set<TechnicianHour> findTechnicianHourByDate(Date date);
}