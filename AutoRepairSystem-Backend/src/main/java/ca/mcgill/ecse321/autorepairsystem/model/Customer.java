package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User{
   private Set<Appointment> appointment;
   
   @OneToMany(mappedBy="customer", cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   }
