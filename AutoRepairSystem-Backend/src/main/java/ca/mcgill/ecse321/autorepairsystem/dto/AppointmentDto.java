package ca.mcgill.ecse321.autorepairsystem.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

public class AppointmentDto {
	private Set <WorkItemDto> services;
	private TechnicianDto technician;
	private CustomerDto customer;
	private Integer id;
	private Time starttime;
	private Time endtime;
	private Date date;
	
	public AppointmentDto() {}
	
	public AppointmentDto(Set <WorkItemDto> services,TechnicianDto technician,CustomerDto customer,Integer id, Time starttime, Time endtime, Date date) {
		this.services = services;
		this.technician = technician;
		this.customer = customer;
		this.id = id;
		this.starttime = starttime;
		this.endtime = endtime;
		this.date = date;
			
	}
	
	public void setServices(Set <WorkItemDto> services) {
		this.services = services;
	}
	
	public Set <WorkItemDto> getServices(){
		return services;
	}
	
	public void setTechnician(TechnicianDto technician) {
		this.technician = technician;
	}
	
	public TechnicianDto getTechnician() {
		return technician;
	}
	
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
	
	public CustomerDto getCustomer() {
		return customer;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
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
	
}
