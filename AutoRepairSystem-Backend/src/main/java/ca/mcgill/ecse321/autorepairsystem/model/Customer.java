package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;

@Entity
public class Customer extends User{
	
	private int amountOwed;
	
	public void setAmountOwed(int amountOwed) {
		this.amountOwed = amountOwed;
	}
	
	public int getAmountOwed() {
		return this.amountOwed;
	}	
}
