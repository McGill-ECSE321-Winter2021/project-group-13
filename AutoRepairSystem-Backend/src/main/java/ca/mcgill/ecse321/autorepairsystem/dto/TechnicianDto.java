package ca.mcgill.ecse321.autorepairsystem.dto;

import java.util.Collections;
import java.util.Set;

public class TechnicianDto extends EndUserDto {
	
	private Set <TechnicianHourDto> technicianhours;
	
	public TechnicianDto() {}
	
	@SuppressWarnings("unchecked")
	public TechnicianDto(String email) {
		this(null, null, null, email, null, Collections.EMPTY_SET);
	}
	
	public TechnicianDto(String username, String password, String name, String email, String userType, Set<TechnicianHourDto> technicianhours) {
		super(username, password, name, email,userType);
		this.technicianhours = technicianhours;
		
	}

	
	
	public Set <TechnicianHourDto> getTechnicianHours(){
		return technicianhours;
	}
	
	public void setTechnicianHours(Set <TechnicianHourDto> technicianhours) {
		this.technicianhours = technicianhours;
	}
	
	
	
}
