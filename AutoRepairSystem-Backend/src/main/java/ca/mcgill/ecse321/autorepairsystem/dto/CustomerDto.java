package ca.mcgill.ecse321.autorepairsystem.dto;

public class CustomerDto extends EndUserDto {
	
	private int amountOwed;
	
	public CustomerDto() {}
	
	
	public CustomerDto(String email) {
		this(null, null, null, email,null, 0);
	}
	
	public CustomerDto(String username, String password, String name, String email,String userType, int amountOwed) {
		super(username, password, name, email,userType);
		this.amountOwed= amountOwed;
		
		
	}
	
	public void setAmountOwed(int amountOwed) {
		this.amountOwed = amountOwed;
	}
	
	public int getAmountOwed() {
		return amountOwed;
	}
	

	
}
