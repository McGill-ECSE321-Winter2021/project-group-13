package ca.mcgill.ecse321.autorepairsystem.dao;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.BusinessHour;

public interface BusinessHourRepository extends CrudRepository<BusinessHour, String>{
	BusinessHour findBusinessHourById (Integer id);
	Set<BusinessHour> findBusinessHourByDate(Date date);
}