package ca.mcgill.ecse321.autorepairsystem.dto;

import java.util.Collections;
import java.util.Set;

public class CustomerDto extends EndUserDto {
	
	private int amountOwed;
	
	public CustomerDto() {}
	
	
	public CustomerDto(String email) {
		this(null, null, null, email, 0);
	}
	
	public CustomerDto(String username, String password, String name, String email, int amountOwed) {
		super(username, password, name, email);
		this.amountOwed= amountOwed;
		
		
	}
	
	public void setAmountOwed(int amountOwed) {
		this.amountOwed = amountOwed;
	}
	
	public int getAmountOwed() {
		return amountOwed;
	}
	

	
}
