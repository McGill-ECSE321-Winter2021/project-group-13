package ca.mcgill.ecse321.autorepairsystem.dto;

import java.util.Collections;
import java.util.Set;

public class AppointmentManagerDto {

	private Set <EndUserDto> users;
	private Set <WorkItemDto> services;
	private Set <BusinessHourDto> businesshours;
	private Set <AppointmentDto> appointments;
	
	public AppointmentManagerDto() {}
	
	@SuppressWarnings("unchecked")
	public AppointmentManagerDto(Set <WorkItemDto> services,Set <BusinessHourDto> businesshours) {
		this(Collections.EMPTY_SET, services, businesshours, Collections.EMPTY_SET);
	}
	
	public AppointmentManagerDto(Set <EndUserDto> users, Set <WorkItemDto> services,Set <BusinessHourDto> businesshours, Set <AppointmentDto> appointments) {
		this.users = users;
		this.services = services;
		this.businesshours = businesshours;
		this.appointments = appointments;
	}
	
	public Set <AppointmentDto> getAppointments(){
		return appointments;
	}
	
	public void setAppointments(Set <AppointmentDto> appointments) {
		this.appointments = appointments;
	}
	
	public Set <EndUserDto> getUsers(){
		return users;
	}
	
	public void setUsers(Set <EndUserDto> users) {
		this.users = users;
	}
	
	public Set <BusinessHourDto> getBusinessHours(){
		return businesshours;
	}
	
	public void setBusinessHours(Set <BusinessHourDto> businesshours) {
		this.businesshours = businesshours;
	}
	
	public Set <WorkItemDto> getServices(){
		return services;
	}
	
	public void setServices(Set <WorkItemDto> services) {
		this.services = services;
	}
	
	
}
