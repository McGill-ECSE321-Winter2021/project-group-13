package ca.mcgill.ecse321.eventregistration.dto;

public class UserDto {

	private String username;
	private String password;
	private String name;
	private String email;
	
	public UserDto() {
		
	}
	
	public UserDto(String email) {
		this(null,null,null,email);
	}

	public UserDto(String username, String password, String name, String email){
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		
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
	
	public void setUsername(String username) {
		this.username = username;
		
	}
	
	public void setName(String name) {
		this.name=name;
		
	}
	
	public void setEmail(String email) {
		this.email= email;
		
	}
	
	public void setPassword(String password) {
		this.password = password;
		
	}
	
	
	
	
	
}
