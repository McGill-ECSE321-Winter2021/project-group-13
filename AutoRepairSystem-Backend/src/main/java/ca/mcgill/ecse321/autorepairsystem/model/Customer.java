package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name ="customerUsername")
public class Customer extends EndUser{
	
	private int amountOwed;
	
	public void setAmountOwed(int amountOwed) {
		this.amountOwed = amountOwed;
	}
	
	public int getAmountOwed() {
		return this.amountOwed;
	}
}
