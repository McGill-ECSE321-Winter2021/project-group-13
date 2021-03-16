package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WorkItem{
   private String name;
	   
	public void setName(String value) {
	    this.name = value;
	}
	@Id
	public String getName() {
	    return this.name;
	}
	private int duration;
	
	public void setDuration(int value) {
	    this.duration = value;
	}
	public int getDuration() {
	    return this.duration;
	}
	private int price;
	
	public void setPrice(int value) {
	    this.price = value;
	}
	public int getPrice() {
	    return this.price;
	}
	
}
