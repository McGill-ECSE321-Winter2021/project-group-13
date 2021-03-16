package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Technician extends User{
   private Set<TechnicianHour> technicianHour;
   
   @OneToMany(mappedBy="technician" , cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
   public Set<TechnicianHour> getTechnicianHour() {
      return this.technicianHour;
   }
   
   public void setTechnicianHour(Set<TechnicianHour> technicianHours) {
      this.technicianHour = technicianHours;
   }
}
