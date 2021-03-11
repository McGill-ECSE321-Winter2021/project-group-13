package ca.mcgill.ecse321.autorepairsystem.dto;

public class ServiceDto {

	private String name;
	private int duration;
	private int price;
	
	public ServiceDto() {}
	
	public ServiceDto(String name, int duration, int price) {
		this.name = name;
		this.duration = duration;
		this.price = price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
}
