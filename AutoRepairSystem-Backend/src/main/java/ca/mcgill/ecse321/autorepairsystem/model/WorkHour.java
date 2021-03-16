package ca.mcgill.ecse321.autorepairsystem.model;

import javax.persistence.Entity;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class WorkHour{
   private Time startTime;

public void setStartTime(Time value) {
    this.startTime = value;
}
public Time getStartTime() {
    return this.startTime;
}
private Time endTime;

public void setEndTime(Time value) {
    this.endTime = value;
}
public Time getEndTime() {
    return this.endTime;
}
private Date date;

public void setDate(Date value) {
    this.date = value;
}
public Date getDate() {
    return this.date;
}
private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
   private Set<WorkBreak> workBreak;
   
   @OneToMany(mappedBy="workHour" , cascade={CascadeType.ALL})
   public Set<WorkBreak> getWorkBreak() {
      return this.workBreak;
   }
   
   public void setWorkBreak(Set<WorkBreak> workBreaks) {
      this.workBreak = workBreaks;
   }
   
   }
