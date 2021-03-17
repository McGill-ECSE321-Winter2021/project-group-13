package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "technicianID")
public class Technician extends EndUser{
   private Set<TechnicianHour> technicianHour;
   
   @OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
   @JoinColumn(name ="technicianHourId")
   public Set<TechnicianHour> getTechnicianHour() {
      return this.technicianHour;
   }
   
   public void setTechnicianHour(Set<TechnicianHour> technicianHours) {
      this.technicianHour = technicianHours;
   }
}
