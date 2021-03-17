package ca.mcgill.ecse321.autorepairsystem.dto;

public class EndUserDto {

	private String username;
	private String password;
	private String name;
	private String email;
	
	public EndUserDto() {
		
	}
	
	
	public EndUserDto(String email) {
		this(null,null,null,email);
	}

	public EndUserDto(String username, String password, String name, String email){
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
