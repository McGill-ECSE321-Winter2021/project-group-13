/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 7 "../../../../../AutoRepairSystem.ump"
public class Business
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Associations
  private AppointmentManager appointmentManager;
  private List<BusinessHour> businessHours;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(AppointmentManager aAppointmentManager)
  {
    boolean didAddAppointmentManager = setAppointmentManager(aAppointmentManager);
    if (!didAddAppointmentManager)
    {
      throw new RuntimeException("Unable to create business due to appointmentManager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    businessHours = new ArrayList<BusinessHour>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public AppointmentManager getAppointmentManager()
  {
    return appointmentManager;
  }
  /* Code from template association_GetMany */
  public BusinessHour getBusinessHour(int index)
  {
    BusinessHour aBusinessHour = businessHours.get(index);
    return aBusinessHour;
  }

  public List<BusinessHour> getBusinessHours()
  {
    List<BusinessHour> newBusinessHours = Collections.unmodifiableList(businessHours);
    return newBusinessHours;
  }

  public int numberOfBusinessHours()
  {
    int number = businessHours.size();
    return number;
  }

  public boolean hasBusinessHours()
  {
    boolean has = businessHours.size() > 0;
    return has;
  }

  public int indexOfBusinessHour(BusinessHour aBusinessHour)
  {
    int index = businessHours.indexOf(aBusinessHour);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAppointmentManager(AppointmentManager aAppointmentManager)
  {
    boolean wasSet = false;
    if (aAppointmentManager == null)
    {
      return wasSet;
    }

    AppointmentManager existingAppointmentManager = appointmentManager;
    appointmentManager = aAppointmentManager;
    if (existingAppointmentManager != null && !existingAppointmentManager.equals(aAppointmentManager))
    {
      existingAppointmentManager.removeBusiness(this);
    }
    appointmentManager.addBusiness(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBusinessHours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BusinessHour addBusinessHour(Time aStartTime, Time aEndTime, Date aDate)
  {
    return new BusinessHour(aStartTime, aEndTime, aDate, this);
  }

  public boolean addBusinessHour(BusinessHour aBusinessHour)
  {
    boolean wasAdded = false;
    if (businessHours.contains(aBusinessHour)) { return false; }
    Business existingBusiness = aBusinessHour.getBusiness();
    boolean isNewBusiness = existingBusiness != null && !this.equals(existingBusiness);
    if (isNewBusiness)
    {
      aBusinessHour.setBusiness(this);
    }
    else
    {
      businessHours.add(aBusinessHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusinessHour(BusinessHour aBusinessHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aBusinessHour, as it must always have a business
    if (!this.equals(aBusinessHour.getBusiness()))
    {
      businessHours.remove(aBusinessHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessHourAt(BusinessHour aBusinessHour, int index)
  {  
    boolean wasAdded = false;
    if(addBusinessHour(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHours()) { index = numberOfBusinessHours() - 1; }
      businessHours.remove(aBusinessHour);
      businessHours.add(index, aBusinessHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessHourAt(BusinessHour aBusinessHour, int index)
  {
    boolean wasAdded = false;
    if(businessHours.contains(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHours()) { index = numberOfBusinessHours() - 1; }
      businessHours.remove(aBusinessHour);
      businessHours.add(index, aBusinessHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessHourAt(aBusinessHour, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    AppointmentManager placeholderAppointmentManager = appointmentManager;
    this.appointmentManager = null;
    if(placeholderAppointmentManager != null)
    {
      placeholderAppointmentManager.removeBusiness(this);
    }
    while (businessHours.size() > 0)
    {
      BusinessHour aBusinessHour = businessHours.get(businessHours.size() - 1);
      aBusinessHour.delete();
      businessHours.remove(aBusinessHour);
    }
    
  }

}