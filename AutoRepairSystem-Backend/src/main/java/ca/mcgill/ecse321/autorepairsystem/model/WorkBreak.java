package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import java.sql.Time;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WorkBreak{
   private Time startBreak;

public void setStartBreak(Time value) {
    this.startBreak = value;
}
@Id
public Time getStartBreak() {
    return this.startBreak;
}
private Time endBreak;

public void setEndBreak(Time value) {
    this.endBreak = value;
}
public Time getEndBreak() {
    return this.endBreak;
}
   private WorkHour workHour;
   
   @ManyToOne(optional=false)
   public WorkHour getWorkHour() {
      return this.workHour;
   }
   
   public void setWorkHour(WorkHour workHour) {
      this.workHour = workHour;
   }
   
   }
