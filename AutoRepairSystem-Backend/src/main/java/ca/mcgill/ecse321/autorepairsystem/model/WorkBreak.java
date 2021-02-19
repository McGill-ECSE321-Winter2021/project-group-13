/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.sql.Time;

// line 55 "../../../../../AutoRepairSystem.ump"
public class WorkBreak
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkBreak Attributes
  private Time breakStarts;
  private Time breakEnds;

  //WorkBreak Associations
  private AvailabilitySchedule availabilitySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkBreak(Time aBreakStarts, Time aBreakEnds, AvailabilitySchedule aAvailabilitySchedule)
  {
    breakStarts = aBreakStarts;
    breakEnds = aBreakEnds;
    boolean didAddAvailabilitySchedule = setAvailabilitySchedule(aAvailabilitySchedule);
    if (!didAddAvailabilitySchedule)
    {
      throw new RuntimeException("Unable to create workBreak due to availabilitySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBreakStarts(Time aBreakStarts)
  {
    boolean wasSet = false;
    breakStarts = aBreakStarts;
    wasSet = true;
    return wasSet;
  }

  public boolean setBreakEnds(Time aBreakEnds)
  {
    boolean wasSet = false;
    breakEnds = aBreakEnds;
    wasSet = true;
    return wasSet;
  }

  public Time getBreakStarts()
  {
    return breakStarts;
  }

  public Time getBreakEnds()
  {
    return breakEnds;
  }
  /* Code from template association_GetOne */
  public AvailabilitySchedule getAvailabilitySchedule()
  {
    return availabilitySchedule;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAvailabilitySchedule(AvailabilitySchedule aNewAvailabilitySchedule)
  {
    boolean wasSet = false;
    if (aNewAvailabilitySchedule == null)
    {
      //Unable to setAvailabilitySchedule to null, as workBreak must always be associated to a availabilitySchedule
      return wasSet;
    }
    
    WorkBreak existingWorkBreak = aNewAvailabilitySchedule.getWorkBreak();
    if (existingWorkBreak != null && !equals(existingWorkBreak))
    {
      //Unable to setAvailabilitySchedule, the current availabilitySchedule already has a workBreak, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AvailabilitySchedule anOldAvailabilitySchedule = availabilitySchedule;
    availabilitySchedule = aNewAvailabilitySchedule;
    availabilitySchedule.setWorkBreak(this);

    if (anOldAvailabilitySchedule != null)
    {
      anOldAvailabilitySchedule.setWorkBreak(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AvailabilitySchedule existingAvailabilitySchedule = availabilitySchedule;
    availabilitySchedule = null;
    if (existingAvailabilitySchedule != null)
    {
      existingAvailabilitySchedule.setWorkBreak(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "breakStarts" + "=" + (getBreakStarts() != null ? !getBreakStarts().equals(this)  ? getBreakStarts().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "breakEnds" + "=" + (getBreakEnds() != null ? !getBreakEnds().equals(this)  ? getBreakEnds().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "availabilitySchedule = "+(getAvailabilitySchedule()!=null?Integer.toHexString(System.identityHashCode(getAvailabilitySchedule())):"null");
  }
}