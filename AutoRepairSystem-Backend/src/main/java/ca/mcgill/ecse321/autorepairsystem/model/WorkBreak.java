package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.sql.Time;
import javax.persistence.Id;

@Entity
public class WorkBreak{
	
	private String workBreakId;
	
	public void setWorkBreakId(String value) {
	    this.workBreakId = value;
	}
	@Id
	@GeneratedValue
	public String getWorkBreakId() {
	    return this.workBreakId;
	}
	
	private Time startBreak;
	
	public void setStartBreak(Time value) {
	    this.startBreak = value;
	}

	public Time getStartBreak() {
	    return this.startBreak;
	}
	private Time endBreak;
	
	public void setEndBreak(Time value) {
	    this.endBreak = value;
	}
	public Time getEndBreak() {
	    return this.endBreak;
	}
}
