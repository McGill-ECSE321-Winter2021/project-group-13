package ca.mcgill.ecse321.autorepairsystem.dto;

import java.util.Collections;
import java.util.Set;

public class TechnicianDto extends UserDto {
	
	private Set <AppointmentDto> appointments;
	private Set <TechnicianHourDto> technicianhours;
	
	public TechnicianDto() {}
	
	@SuppressWarnings("unchecked")
	public TechnicianDto(String email) {
		this(null, null, null, email, Collections.EMPTY_SET, Collections.EMPTY_SET);
	}
	
	public TechnicianDto(String username, String password, String name, String email ,Set<AppointmentDto> appointments,Set<TechnicianHourDto> technicianhours) {
		super(username, password, name, email);
		this.appointments = appointments;
		this.technicianhours = technicianhours;
		
	}

	public Set <AppointmentDto> getAppointments(){
		return appointments;
	}
	
	public void setAppointments(Set <AppointmentDto> appointments) {
		this.appointments = appointments;
	}
	
	public Set <TechnicianHourDto> getTechnicianHours(){
		return technicianhours;
	}
	
	public void setTechnicianHours(Set <TechnicianHourDto> technicianhours) {
		this.technicianhours = technicianhours;
	}
	
	
	
}
