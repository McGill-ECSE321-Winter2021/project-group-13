package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User{
   private Set<Appointment> appointment;
   
   @OneToMany(mappedBy="customer" )
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   }
