package ca.mcgill.ecse321.autorepairsystem.dto;

import java.sql.Time;

public class WorkBreakDto {
	
	private Time startbreak;
	private Time endbreak;
	private Integer Id;
	
	public WorkBreakDto() {
		
	}
	
	@SuppressWarnings("unchecked")
	public WorkBreakDto(Integer Id) {
		this(null,null,Id);
		
	}
	
	public WorkBreakDto(Time startbreak, Time endbreak, Integer Id) {
		this.startbreak = startbreak;
		this.endbreak = endbreak;
		this.Id=Id;
		
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
	
	public void setId(Integer Id) {
		this.Id = Id;
	}
	
	public Integer getId() {
		return Id;
	}
		
	

}
