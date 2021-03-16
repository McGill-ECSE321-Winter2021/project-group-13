package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.JoinColumn;
@Entity
public class Appointment{
   private Set<WorkItem> workItem;
   
   @ManyToMany(cascade = {CascadeType.ALL})
   public Set<WorkItem> getWorkItem() {
      return this.workItem;
   }
   
   public void setWorkItem(Set<WorkItem> workItems) {
      this.workItem = workItems;
   }
   
   private Technician technician;
   
   @ManyToOne(optional=true, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
   @JoinColumn(name="technicianUsername")
   public Technician getTechnician() {
      return this.technician;
   }
   
   public void setTechnician(Technician technician) {
      this.technician = technician;
   }
   
   private Customer customer;
   
   @ManyToOne(optional=true, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
   @JoinColumn(name="customerUsername")
   public Customer getCustomer() {
      return this.customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   private Integer id;

	public void setId(Integer value) {
	    this.id = value;
	}
	@Id
	public Integer getId() {
	    return this.id;
	}
	
	private Time startTime;
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	private Time endTime;
	
	public void setEndTime(Time endTime) {
		this.startTime = endTime;
	}
	
	public Time getEndTime() {
		return this.endTime;
	}
	
	private Date date;
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}



}
