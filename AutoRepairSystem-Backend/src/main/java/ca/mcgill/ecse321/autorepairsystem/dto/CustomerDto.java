package ca.mcgill.ecse321.eventregistration.dto;

import java.util.Collections;
import java.util.Set;

public class CustomerDto extends UserDto {
	
	private Set <AppointmentDto> appointments;
	
	public CustomerDto() {}
	
	@SuppressWarnings("unchecked")
	public CustomerDto(String email) {
		this(null, null, null, email, Collections.EMPTY_SET);
	}
	
	public CustomerDto(String username, String password, String name, String email ,Set<AppointmentDto> appointments) {
		super(username, password, name, email);
		this.appointments = appointments;
		
	}
	
	public Set <AppointmentDto> getAppointments(){
		return appointments;
	}
	
	public void setAppointments(Set <AppointmentDto> appointments) {
		this.appointments = appointments;
	}

	
}
