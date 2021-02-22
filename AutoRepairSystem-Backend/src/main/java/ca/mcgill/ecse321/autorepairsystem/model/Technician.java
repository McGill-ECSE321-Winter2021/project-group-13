package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Technician extends User{
   private Set<TechnicianHour> technicianHour;
   
   @OneToMany(mappedBy="technician" , cascade={CascadeType.ALL})
   public Set<TechnicianHour> getTechnicianHour() {
      return this.technicianHour;
   }
   
   public void setTechnicianHour(Set<TechnicianHour> technicianHours) {
      this.technicianHour = technicianHours;
   }
   
   private Set<Appointment> appointment;
   
   @OneToMany(mappedBy="technician" )
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   }
