package ca.mcgill.ecse321.autorepairsystem.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class EndUser{
   private String username;

	public void setUsername(String value) {
	    this.username = value;
	}
	
	@Id
	public String getUsername() {
	    return this.username;
	}
	private String password;
	
	public void setPassword(String value) {
	    this.password = value;
	}
	public String getPassword() {
	    return this.password;
	}
	private String name;
	
	public void setName(String value) {
	    this.name = value;
	}
	public String getName() {
	    return this.name;
	}
	private String email;
	
	public void setEmail(String value) {
	    this.email = value;
	}
	public String getEmail() {
	    return this.email;
	}
}
