/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.sql.Date;
import java.util.*;
import java.sql.Time;

// line 6 "../../AutoRepairSystem.ump"
public class Day
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Day Attributes
  private DayOfWeek dayOfWeek;
  private Date date;

  //Day Associations
  private BusinessHour businessHour;
  private List<TimeSlot> timeSlots;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Day(DayOfWeek aDayOfWeek, Date aDate, BusinessHour aBusinessHour)
  {
    dayOfWeek = aDayOfWeek;
    date = aDate;
    boolean didAddBusinessHour = setBusinessHour(aBusinessHour);
    if (!didAddBusinessHour)
    {
      throw new RuntimeException("Unable to create day due to businessHour. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    timeSlots = new ArrayList<TimeSlot>();
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

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public DayOfWeek getDayOfWeek()
  {
    return dayOfWeek;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public BusinessHour getBusinessHour()
  {
    return businessHour;
  }
  /* Code from template association_GetMany */
  public TimeSlot getTimeSlot(int index)
  {
    TimeSlot aTimeSlot = timeSlots.get(index);
    return aTimeSlot;
  }

  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
  }

  public int numberOfTimeSlots()
  {
    int number = timeSlots.size();
    return number;
  }

  public boolean hasTimeSlots()
  {
    boolean has = timeSlots.size() > 0;
    return has;
  }

  public int indexOfTimeSlot(TimeSlot aTimeSlot)
  {
    int index = timeSlots.indexOf(aTimeSlot);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBusinessHour(BusinessHour aBusinessHour)
  {
    boolean wasSet = false;
    if (aBusinessHour == null)
    {
      return wasSet;
    }

    BusinessHour existingBusinessHour = businessHour;
    businessHour = aBusinessHour;
    if (existingBusinessHour != null && !existingBusinessHour.equals(aBusinessHour))
    {
      existingBusinessHour.removeDay(this);
    }
    businessHour.addDay(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TimeSlot addTimeSlot(Time aStartTime, Time aEndTime, boolean aIsAvailable)
  {
    return new TimeSlot(aStartTime, aEndTime, aIsAvailable, this);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    Day existingDay = aTimeSlot.getDay();
    boolean isNewDay = existingDay != null && !this.equals(existingDay);
    if (isNewDay)
    {
      aTimeSlot.setDay(this);
    }
    else
    {
      timeSlots.add(aTimeSlot);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    //Unable to remove aTimeSlot, as it must always have a day
    if (!this.equals(aTimeSlot.getDay()))
    {
      timeSlots.remove(aTimeSlot);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
  {  
    boolean wasAdded = false;
    if(addTimeSlot(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(timeSlots.contains(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTimeSlotAt(aTimeSlot, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    BusinessHour placeholderBusinessHour = businessHour;
    this.businessHour = null;
    if(placeholderBusinessHour != null)
    {
      placeholderBusinessHour.removeDay(this);
    }
    for(int i=timeSlots.size(); i > 0; i--)
    {
      TimeSlot aTimeSlot = timeSlots.get(i - 1);
      aTimeSlot.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "businessHour = "+(getBusinessHour()!=null?Integer.toHexString(System.identityHashCode(getBusinessHour())):"null");
  }
}