package ca.mcgill.ecse321.autorepairsystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

public class AppointmentManager{
	
	private Set<EndUser> endUser;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<EndUser> getUser() {
      return this.endUser;
   }
   
   public void setUser(Set<EndUser> endUsers) {
      this.endUser = endUsers;
   }
   
   private Set<WorkItem> service;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<WorkItem> getService() {
      return this.service;
   }
   
   public void setService(Set<WorkItem> services) {
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
}
