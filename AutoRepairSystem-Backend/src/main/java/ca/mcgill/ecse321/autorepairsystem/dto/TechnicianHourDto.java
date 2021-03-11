package ca.mcgill.ecse321.autorepairsystem.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.Set;

public class TechnicianHourDto extends WorkHourDto {

	private TechnicianDto technician;
	
	public TechnicianHourDto() {
		
	}
	
	@SuppressWarnings("unchecked")
	public TechnicianHourDto(Integer id) {
		this(null, null, null, id, Collections.EMPTY_SET, null);
	}
	
	public TechnicianHourDto(Time starttime, Time endtime, Date date, Integer id, Set <WorkBreakDto> workbreaks, TechnicianDto technician) {
		super(starttime, endtime, date, id, workbreaks);
		this.technician = technician;
	}
	
	public void setTechnician(TechnicianDto technician) {
		this.technician = technician;
	}
	
	public TechnicianDto getTechnician() {
		return technician;
	}
	
	
}
