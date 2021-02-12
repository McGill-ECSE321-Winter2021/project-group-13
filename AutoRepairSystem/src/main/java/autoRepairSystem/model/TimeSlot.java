/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.sql.Time;

// line 18 "../../AutoRepairSystem.ump"
public class TimeSlot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private Time startTime;
  private Time endTime;
  private boolean isAvailable;

  //TimeSlot Associations
  private Day day;
  private Appointment appointment;
  private Schedule schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Time aStartTime, Time aEndTime, boolean aIsAvailable, Day aDay)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    isAvailable = aIsAvailable;
    boolean didAddDay = setDay(aDay);
    if (!didAddDay)
    {
      throw new RuntimeException("Unable to create timeSlot due to day. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setIsAvailable(boolean aIsAvailable)
  {
    boolean wasSet = false;
    isAvailable = aIsAvailable;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public boolean getIsAvailable()
  {
    return isAvailable;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsAvailable()
  {
    return isAvailable;
  }
  /* Code from template association_GetOne */
  public Day getDay()
  {
    return day;
  }
  /* Code from template association_GetOne */
  public Appointment getAppointment()
  {
    return appointment;
  }

  public boolean hasAppointment()
  {
    boolean has = appointment != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Schedule getSchedule()
  {
    return schedule;
  }

  public boolean hasSchedule()
  {
    boolean has = schedule != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDay(Day aDay)
  {
    boolean wasSet = false;
    if (aDay == null)
    {
      return wasSet;
    }

    Day existingDay = day;
    day = aDay;
    if (existingDay != null && !existingDay.equals(aDay))
    {
      existingDay.removeTimeSlot(this);
    }
    day.addTimeSlot(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAppointment(Appointment aNewAppointment)
  {
    boolean wasSet = false;
    if (appointment != null && !appointment.equals(aNewAppointment) && equals(appointment.getTimeSlot()))
    {
      //Unable to setAppointment, as existing appointment would become an orphan
      return wasSet;
    }

    appointment = aNewAppointment;
    TimeSlot anOldTimeSlot = aNewAppointment != null ? aNewAppointment.getTimeSlot() : null;

    if (!this.equals(anOldTimeSlot))
    {
      if (anOldTimeSlot != null)
      {
        anOldTimeSlot.appointment = null;
      }
      if (appointment != null)
      {
        appointment.setTimeSlot(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setSchedule(Schedule aSchedule)
  {
    boolean wasSet = false;
    Schedule existingSchedule = schedule;
    schedule = aSchedule;
    if (existingSchedule != null && !existingSchedule.equals(aSchedule))
    {
      existingSchedule.removeTimeSlot(this);
    }
    if (aSchedule != null)
    {
      aSchedule.addTimeSlot(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Day placeholderDay = day;
    this.day = null;
    if(placeholderDay != null)
    {
      placeholderDay.removeTimeSlot(this);
    }
    Appointment existingAppointment = appointment;
    appointment = null;
    if (existingAppointment != null)
    {
      existingAppointment.delete();
    }
    if (schedule != null)
    {
      Schedule placeholderSchedule = schedule;
      this.schedule = null;
      placeholderSchedule.removeTimeSlot(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isAvailable" + ":" + getIsAvailable()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "day = "+(getDay()!=null?Integer.toHexString(System.identityHashCode(getDay())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointment = "+(getAppointment()!=null?Integer.toHexString(System.identityHashCode(getAppointment())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "schedule = "+(getSchedule()!=null?Integer.toHexString(System.identityHashCode(getSchedule())):"null");
  }
}