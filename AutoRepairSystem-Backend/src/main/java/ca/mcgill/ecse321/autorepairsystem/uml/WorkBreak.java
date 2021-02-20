/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.sql.Time;

// line 70 "../../../../../AutoRepairSystem.ump"
public class WorkBreak
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkBreak Attributes
  private Time breakStarts;
  private Time breakEnds;

  //WorkBreak Associations
  private WorkHour workHour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkBreak(Time aBreakStarts, Time aBreakEnds, WorkHour aWorkHour)
  {
    breakStarts = aBreakStarts;
    breakEnds = aBreakEnds;
    boolean didAddWorkHour = setWorkHour(aWorkHour);
    if (!didAddWorkHour)
    {
      throw new RuntimeException("Unable to create workBreak due to workHour. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public WorkHour getWorkHour()
  {
    return workHour;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWorkHour(WorkHour aWorkHour)
  {
    boolean wasSet = false;
    if (aWorkHour == null)
    {
      return wasSet;
    }

    WorkHour existingWorkHour = workHour;
    workHour = aWorkHour;
    if (existingWorkHour != null && !existingWorkHour.equals(aWorkHour))
    {
      existingWorkHour.removeWorkBreak(this);
    }
    workHour.addWorkBreak(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    WorkHour placeholderWorkHour = workHour;
    this.workHour = null;
    if(placeholderWorkHour != null)
    {
      placeholderWorkHour.removeWorkBreak(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "breakStarts" + "=" + (getBreakStarts() != null ? !getBreakStarts().equals(this)  ? getBreakStarts().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "breakEnds" + "=" + (getBreakEnds() != null ? !getBreakEnds().equals(this)  ? getBreakEnds().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "workHour = "+(getWorkHour()!=null?Integer.toHexString(System.identityHashCode(getWorkHour())):"null");
  }
}