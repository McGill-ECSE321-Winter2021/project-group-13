package ca.mcgill.ecse321.autorepairsystem.dto;

import java.sql.Time;

public class WorkBreakDto {
	
	private Time startbreak;
	private Time endbreak;
	private WorkHourDto workhour;
	
	public WorkBreakDto() {
		
	}
	
	public WorkBreakDto(WorkHourDto workhour) {
		this(null, null, workhour);
	}
	
	public WorkBreakDto(Time startbreak, Time endbreak, WorkHourDto workhour) {
		this.startbreak = startbreak;
		this.endbreak = endbreak;
		this.workhour = workhour;
	}
	
	public void setStartBreak(Time startbreak) {
		this.startbreak = startbreak;
	}
	
	public Time getStartBreak() {
		return startbreak;
	}
	
	public void setEndBreak(Time endbreak) {
		this.endbreak = endbreak;
	}
	
	public Time getEndBreak() {
		return endbreak;
	}
	
	public void setWorkHour(WorkHourDto workhour) {
		this.workhour = workhour;
	}
	
	
	public WorkHourDto getWorkHour() {
		return workhour;
	}
	
	
	

}
