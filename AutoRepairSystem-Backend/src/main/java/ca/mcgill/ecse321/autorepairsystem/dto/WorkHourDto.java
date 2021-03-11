package ca.mcgill.ecse321.autorepairsystem.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.Set;

public class WorkHourDto {
	
	private Time starttime;
	private Time endtime;
	private Date date;
	private Integer id;
	private Set <WorkBreakDto> workbreaks;
	
	public WorkHourDto() {
		
	}
	
	@SuppressWarnings("unchecked")
	public WorkHourDto(Integer id) {
		this(null, null, null, id, Collections.EMPTY_SET);
		
	}	
	
	
	public WorkHourDto(Time starttime, Time endtime, Date date, Integer id, Set <WorkBreakDto> workbreaks) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.date = date;
		this.id = id;
		this.workbreaks = workbreaks;
	}
	
	public void setStartTime(Time starttime) {
		this.starttime = starttime;
	}
	
	public Time getStartTime() {
		return starttime;
	}
	
	public void setEndTime(Time endtime) {
		this.endtime = endtime;
	}
	
	public Time getEndTime() {
		return endtime;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setWorkBreaks(Set <WorkBreakDto> workbreaks) {
		this.workbreaks = workbreaks;
	}
	
	public Set <WorkBreakDto> getWorkBreaks() {
		return workbreaks;
	}
	
	

}
