package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TechnicianHour extends WorkHour{
   private Technician technician;
   
   @ManyToOne(optional=false)
   public Technician getTechnician() {
      return this.technician;
   }
   
   public void setTechnician(Technician technician) {
      this.technician = technician;
   }
   
   }
