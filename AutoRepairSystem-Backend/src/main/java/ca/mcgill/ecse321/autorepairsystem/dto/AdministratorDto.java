package ca.mcgill.ecse321.autorepairsystem.dto;

public class AdministratorDto extends EndUserDto {

	public AdministratorDto() {
		
	}
	
	public AdministratorDto(String email) {
		this(null,null,null,email,null);
	}

	public AdministratorDto(String username, String password, String name, String email, String userType){
		super(username, password, name, email, userType);
		
	}
}
