package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

public class AppointmentManager{
	
	private Set<User> user;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<User> getUser() {
      return this.user;
   }
   
   public void setUser(Set<User> users) {
      this.user = users;
   }
   
   private Set<Service> service;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Service> getService() {
      return this.service;
   }
   
   public void setService(Set<Service> services) {
      this.service = services;
   }
   
   private Set<BusinessHour> businessHour;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<BusinessHour> getBusinessHour() {
      return this.businessHour;
   }
   
   public void setBusinessHour(Set<BusinessHour> businessHours) {
      this.businessHour = businessHours;
   }
   
   private Set<Appointment> appointment;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   private String businessName;
   
   }
