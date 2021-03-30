package ca.mcgill.ecse321.autorepairsystem.dto;

public class EndUserDto {

	private String username;
	private String password;
	private String name;
	private String email;
	private String userType;
	
	public EndUserDto() {
		
	}
	
	
	public EndUserDto(String email) {
		this(null,null,null,email,null);
	}

	public EndUserDto(String username, String password, String name, String email, String userType){
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.userType = userType;
		
	}
	
	
	public String getUsername() {
		return username;
		
	}
	
	public String getName() {
		return name;
		
	}
	
	public String getEmail() {
		return email;
		
	}
	
	public String getPassword() {
		return password;
		
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUsername(String username) {
		this.username = username;
		
	}
	
	public void setName(String name) {
		this.name=name;
		
	}
	
	public void setEmail(String email) {
		this.email= email;
		
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public void setPassword(String password) {
		this.password = password;
		
	}
	
	
	
	
	
}
