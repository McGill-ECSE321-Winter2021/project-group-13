/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.util.*;

// line 62 "../../AutoRepairSystem.ump"
public class Schedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Schedule Associations
  private Technician technician;
  private List<TimeSlot> timeSlots;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Schedule(Technician aTechnician)
  {
    if (aTechnician == null || aTechnician.getSchedule() != null)
    {
      throw new RuntimeException("Unable to create Schedule due to aTechnician. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    technician = aTechnician;
    timeSlots = new ArrayList<TimeSlot>();
  }

  public Schedule(String aUsernameForTechnician, String aPasswordForTechnician, String aNameForTechnician, String aEmailForTechnician, AutoRepairSystem aAutoRepairSystemForTechnician)
  {
    technician = new Technician(aUsernameForTechnician, aPasswordForTechnician, aNameForTechnician, aEmailForTechnician, aAutoRepairSystemForTechnician, this);
    timeSlots = new ArrayList<TimeSlot>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Technician getTechnician()
  {
    return technician;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    Schedule existingSchedule = aTimeSlot.getSchedule();
    if (existingSchedule == null)
    {
      aTimeSlot.setSchedule(this);
    }
    else if (!this.equals(existingSchedule))
    {
      existingSchedule.removeTimeSlot(aTimeSlot);
      addTimeSlot(aTimeSlot);
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
    if (timeSlots.contains(aTimeSlot))
    {
      timeSlots.remove(aTimeSlot);
      aTimeSlot.setSchedule(null);
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
    Technician existingTechnician = technician;
    technician = null;
    if (existingTechnician != null)
    {
      existingTechnician.delete();
    }
    while( !timeSlots.isEmpty() )
    {
      timeSlots.get(0).setSchedule(null);
    }
  }

}