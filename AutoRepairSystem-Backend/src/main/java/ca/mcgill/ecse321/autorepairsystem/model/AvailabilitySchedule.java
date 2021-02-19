/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.sql.Time;

// line 6 "../../../../../AutoRepairSystem.ump"
public class AvailabilitySchedule
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AvailabilitySchedule Attributes
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  //AvailabilitySchedule Associations
  private AppointmentManager appointmentManager;
  private Technician technician;
  private WorkBreak workBreak;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AvailabilitySchedule(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDayOfWeek(DayOfWeek aDayOfWeek)
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public DayOfWeek getDayOfWeek()
  {
    return dayOfWeek;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  public AppointmentManager getAppointmentManager()
  {
    return appointmentManager;
  }

  public boolean hasAppointmentManager()
  {
    boolean has = appointmentManager != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Technician getTechnician()
  {
    return technician;
  }

  public boolean hasTechnician()
  {
    boolean has = technician != null;
    return has;
  }
  /* Code from template association_GetOne */
  public WorkBreak getWorkBreak()
  {
    return workBreak;
  }

  public boolean hasWorkBreak()
  {
    boolean has = workBreak != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setAppointmentManager(AppointmentManager aAppointmentManager)
  {
    boolean wasSet = false;
    AppointmentManager existingAppointmentManager = appointmentManager;
    appointmentManager = aAppointmentManager;
    if (existingAppointmentManager != null && !existingAppointmentManager.equals(aAppointmentManager))
    {
      existingAppointmentManager.removeBusinessHour(this);
    }
    if (aAppointmentManager != null)
    {
      aAppointmentManager.addBusinessHour(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setTechnician(Technician aTechnician)
  {
    boolean wasSet = false;
    Technician existingTechnician = technician;
    technician = aTechnician;
    if (existingTechnician != null && !existingTechnician.equals(aTechnician))
    {
      existingTechnician.removeWorkHour(this);
    }
    if (aTechnician != null)
    {
      aTechnician.addWorkHour(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setWorkBreak(WorkBreak aNewWorkBreak)
  {
    boolean wasSet = false;
    if (workBreak != null && !workBreak.equals(aNewWorkBreak) && equals(workBreak.getAvailabilitySchedule()))
    {
      //Unable to setWorkBreak, as existing workBreak would become an orphan
      return wasSet;
    }

    workBreak = aNewWorkBreak;
    AvailabilitySchedule anOldAvailabilitySchedule = aNewWorkBreak != null ? aNewWorkBreak.getAvailabilitySchedule() : null;

    if (!this.equals(anOldAvailabilitySchedule))
    {
      if (anOldAvailabilitySchedule != null)
      {
        anOldAvailabilitySchedule.workBreak = null;
      }
      if (workBreak != null)
      {
        workBreak.setAvailabilitySchedule(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (appointmentManager != null)
    {
      AppointmentManager placeholderAppointmentManager = appointmentManager;
      this.appointmentManager = null;
      placeholderAppointmentManager.removeBusinessHour(this);
    }
    if (technician != null)
    {
      Technician placeholderTechnician = technician;
      this.technician = null;
      placeholderTechnician.removeWorkHour(this);
    }
    WorkBreak existingWorkBreak = workBreak;
    workBreak = null;
    if (existingWorkBreak != null)
    {
      existingWorkBreak.delete();
      existingWorkBreak.setAvailabilitySchedule(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentManager = "+(getAppointmentManager()!=null?Integer.toHexString(System.identityHashCode(getAppointmentManager())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "technician = "+(getTechnician()!=null?Integer.toHexString(System.identityHashCode(getTechnician())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "workBreak = "+(getWorkBreak()!=null?Integer.toHexString(System.identityHashCode(getWorkBreak())):"null");
  }
}