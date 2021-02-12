/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.sql.Time;
import java.util.*;
import java.sql.Date;

// line 12 "../../AutoRepairSystem.ump"
public class BusinessHour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BusinessHour Attributes
  private Time openTime;
  private Time closeTime;

  //BusinessHour Associations
  private List<Day> daies;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BusinessHour(Time aOpenTime, Time aCloseTime)
  {
    openTime = aOpenTime;
    closeTime = aCloseTime;
    daies = new ArrayList<Day>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpenTime(Time aOpenTime)
  {
    boolean wasSet = false;
    openTime = aOpenTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCloseTime(Time aCloseTime)
  {
    boolean wasSet = false;
    closeTime = aCloseTime;
    wasSet = true;
    return wasSet;
  }

  public Time getOpenTime()
  {
    return openTime;
  }

  public Time getCloseTime()
  {
    return closeTime;
  }
  /* Code from template association_GetMany */
  public Day getDay(int index)
  {
    Day aDay = daies.get(index);
    return aDay;
  }

  public List<Day> getDaies()
  {
    List<Day> newDaies = Collections.unmodifiableList(daies);
    return newDaies;
  }

  public int numberOfDaies()
  {
    int number = daies.size();
    return number;
  }

  public boolean hasDaies()
  {
    boolean has = daies.size() > 0;
    return has;
  }

  public int indexOfDay(Day aDay)
  {
    int index = daies.indexOf(aDay);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDaies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Day addDay(Day.DayOfWeek aDayOfWeek, Date aDate)
  {
    return new Day(aDayOfWeek, aDate, this);
  }

  public boolean addDay(Day aDay)
  {
    boolean wasAdded = false;
    if (daies.contains(aDay)) { return false; }
    BusinessHour existingBusinessHour = aDay.getBusinessHour();
    boolean isNewBusinessHour = existingBusinessHour != null && !this.equals(existingBusinessHour);
    if (isNewBusinessHour)
    {
      aDay.setBusinessHour(this);
    }
    else
    {
      daies.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Day aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a businessHour
    if (!this.equals(aDay.getBusinessHour()))
    {
      daies.remove(aDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDayAt(Day aDay, int index)
  {  
    boolean wasAdded = false;
    if(addDay(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDaies()) { index = numberOfDaies() - 1; }
      daies.remove(aDay);
      daies.add(index, aDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDayAt(Day aDay, int index)
  {
    boolean wasAdded = false;
    if(daies.contains(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDaies()) { index = numberOfDaies() - 1; }
      daies.remove(aDay);
      daies.add(index, aDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDayAt(aDay, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=daies.size(); i > 0; i--)
    {
      Day aDay = daies.get(i - 1);
      aDay.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openTime" + "=" + (getOpenTime() != null ? !getOpenTime().equals(this)  ? getOpenTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closeTime" + "=" + (getCloseTime() != null ? !getCloseTime().equals(this)  ? getCloseTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}