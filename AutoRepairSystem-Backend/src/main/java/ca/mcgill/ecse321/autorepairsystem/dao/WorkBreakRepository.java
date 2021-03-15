package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.WorkHour;

public interface WorkBreakRepository extends CrudRepository<WorkBreak, String>{
	WorkBreak findWorkBreakByStartBreak(Time startbreak);
	WorkBreak findWorkBreakByWorkHour(WorkHour workhour);
	WorkBreak findByWorkHourAndStartBreak(WorkHour workhour, Time startBreak);
}