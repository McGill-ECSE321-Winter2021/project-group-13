package ca.mcgill.ecse321.autorepairsystem.dto;

import java.util.Collections;
import java.util.Set;

public class AppointmentManagerDto {

	private Set <UserDto> users;
	private Set <ServiceDto> services;
	private Set <BusinessHourDto> businesshours;
	private Set <AppointmentDto> appointments;
	
	public AppointmentManagerDto() {}
	
	@SuppressWarnings("unchecked")
	public AppointmentManagerDto(Set <ServiceDto> services,Set <BusinessHourDto> businesshours) {
		this(Collections.EMPTY_SET, services, businesshours, Collections.EMPTY_SET);
	}
	
	public AppointmentManagerDto(Set <UserDto> users, Set <ServiceDto> services,Set <BusinessHourDto> businesshours, Set <AppointmentDto> appointments) {
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
	
	public Set <UserDto> getUsers(){
		return users;
	}
	
	public void setUsers(Set <UserDto> users) {
		this.users = users;
	}
	
	public Set <BusinessHourDto> getBusinessHours(){
		return businesshours;
	}
	
	public void setBusinessHours(Set <BusinessHourDto> businesshours) {
		this.businesshours = businesshours;
	}
	
	public Set <ServiceDto> getServices(){
		return services;
	}
	
	public void setServices(Set <ServiceDto> services) {
		this.services = services;
	}
	
	
}
