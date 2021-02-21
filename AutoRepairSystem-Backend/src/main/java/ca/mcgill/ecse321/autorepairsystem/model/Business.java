/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.util.*;
import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

// line 7 "../../../../../AutoRepairSystem.ump"
@Entity
public class Business
{
	
	private Integer id;
	  
	  @Id
	  public Integer getId() {
		  return this.id;
	  }
	  
	  public void setId(Integer newId) {
		  this.id = newId;
	  }
	
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Associations
  private AppointmentManager appointmentManager;
  
  @OneToOne
  public AppointmentManager getAppointmentManager()
  {
    return appointmentManager;
  }
  
  public void setAppointmentManager(AppointmentManager newAppointmentManager) {
	  this.appointmentManager = newAppointmentManager;
  }
  
  private List<BusinessHour> businessHours;
  
  @OneToMany
  public List<BusinessHour> getBusinessHours()
  {
    List<BusinessHour> newBusinessHours = Collections.unmodifiableList(businessHours);
    return newBusinessHours;
  }
  
  public void setBusinessHours(List<BusinessHour> newBusinessHour) {
	  this.businessHours = newBusinessHour;
  }
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(AppointmentManager aAppointmentManager)
  {
    if (aAppointmentManager == null || aAppointmentManager.getBusiness() != null)
    {
      throw new RuntimeException("Unable to create Business due to aAppointmentManager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentManager = aAppointmentManager;
    businessHours = new ArrayList<BusinessHour>();
  }

  public Business()
  {
    appointmentManager = new AppointmentManager(this);
    businessHours = new ArrayList<BusinessHour>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  /* Code from template association_GetMany */
  public BusinessHour getBusinessHour(int index)
  {
    BusinessHour aBusinessHour = businessHours.get(index);
    return aBusinessHour;
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
    AppointmentManager existingAppointmentManager = appointmentManager;
    appointmentManager = null;
    if (existingAppointmentManager != null)
    {
      existingAppointmentManager.delete();
    }
    while (businessHours.size() > 0)
    {
      BusinessHour aBusinessHour = businessHours.get(businessHours.size() - 1);
      aBusinessHour.delete();
      businessHours.remove(aBusinessHour);
    }
    
  }

}