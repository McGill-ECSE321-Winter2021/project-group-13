/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

// line 42 "../../../../../AutoRepairSystem.ump"
public abstract class WorkHour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkHour Attributes
  private Time startTime;
  private Time endTime;
  private Date date;

  //WorkHour Associations
  private List<WorkBreak> workBreaks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkHour(Time aStartTime, Time aEndTime, Date aDate)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    workBreaks = new ArrayList<WorkBreak>();
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

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
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

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetMany */
  public WorkBreak getWorkBreak(int index)
  {
    WorkBreak aWorkBreak = workBreaks.get(index);
    return aWorkBreak;
  }

  public List<WorkBreak> getWorkBreaks()
  {
    List<WorkBreak> newWorkBreaks = Collections.unmodifiableList(workBreaks);
    return newWorkBreaks;
  }

  public int numberOfWorkBreaks()
  {
    int number = workBreaks.size();
    return number;
  }

  public boolean hasWorkBreaks()
  {
    boolean has = workBreaks.size() > 0;
    return has;
  }

  public int indexOfWorkBreak(WorkBreak aWorkBreak)
  {
    int index = workBreaks.indexOf(aWorkBreak);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkBreaks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkBreak addWorkBreak(Time aBreakStarts, Time aBreakEnds)
  {
    return new WorkBreak(aBreakStarts, aBreakEnds, this);
  }

  public boolean addWorkBreak(WorkBreak aWorkBreak)
  {
    boolean wasAdded = false;
    if (workBreaks.contains(aWorkBreak)) { return false; }
    WorkHour existingWorkHour = aWorkBreak.getWorkHour();
    boolean isNewWorkHour = existingWorkHour != null && !this.equals(existingWorkHour);
    if (isNewWorkHour)
    {
      aWorkBreak.setWorkHour(this);
    }
    else
    {
      workBreaks.add(aWorkBreak);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkBreak(WorkBreak aWorkBreak)
  {
    boolean wasRemoved = false;
    //Unable to remove aWorkBreak, as it must always have a workHour
    if (!this.equals(aWorkBreak.getWorkHour()))
    {
      workBreaks.remove(aWorkBreak);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkBreakAt(WorkBreak aWorkBreak, int index)
  {  
    boolean wasAdded = false;
    if(addWorkBreak(aWorkBreak))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkBreaks()) { index = numberOfWorkBreaks() - 1; }
      workBreaks.remove(aWorkBreak);
      workBreaks.add(index, aWorkBreak);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkBreakAt(WorkBreak aWorkBreak, int index)
  {
    boolean wasAdded = false;
    if(workBreaks.contains(aWorkBreak))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkBreaks()) { index = numberOfWorkBreaks() - 1; }
      workBreaks.remove(aWorkBreak);
      workBreaks.add(index, aWorkBreak);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkBreakAt(aWorkBreak, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (workBreaks.size() > 0)
    {
      WorkBreak aWorkBreak = workBreaks.get(workBreaks.size() - 1);
      aWorkBreak.delete();
      workBreaks.remove(aWorkBreak);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}