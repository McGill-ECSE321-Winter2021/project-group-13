package ca.mcgill.ecse321.autorepairsystem.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.Set;

public class BusinessHourDto extends WorkHourDto {
	public BusinessHourDto() {
		
	}
	
	@SuppressWarnings("unchecked")
	public BusinessHourDto(Integer id) {
		this(null, null, null, id, Collections.EMPTY_SET);
	}
	
	
	public BusinessHourDto(Time starttime, Time endtime, Date date, Integer id, Set <WorkBreakDto> workbreaks) {
		super(starttime, endtime, date, id, workbreaks);
		
	}
	
	
	
}
