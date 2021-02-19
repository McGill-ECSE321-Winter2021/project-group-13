/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

// line 44 "../../../../../AutoRepairSystem.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private Time startTime;
  private Time endTime;
  private Date date;

  //Appointment Associations
  private Technician technician;
  private List<Service> services;
  private Customer customer;
  private AppointmentManager appointmentManager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Time aStartTime, Time aEndTime, Date aDate, AppointmentManager aAppointmentManager, Service... allServices)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    services = new ArrayList<Service>();
    boolean didAddServices = setServices(allServices);
    if (!didAddServices)
    {
      throw new RuntimeException("Unable to create Appointment, must have at least 1 services. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAppointmentManager = setAppointmentManager(aAppointmentManager);
    if (!didAddAppointmentManager)
    {
      throw new RuntimeException("Unable to create appointment due to appointmentManager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }

  public boolean hasCustomer()
  {
    boolean has = customer != null;
    return has;
  }
  /* Code from template association_GetOne */
  public AppointmentManager getAppointmentManager()
  {
    return appointmentManager;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setTechnician(Technician aTechnician)
  {
    boolean wasSet = false;
    Technician existingTechnician = technician;
    technician = aTechnician;
    if (existingTechnician != null && !existingTechnician.equals(aTechnician))
    {
      existingTechnician.removeAppointment(this);
    }
    if (aTechnician != null)
    {
      aTechnician.addAppointment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfServicesValid()
  {
    boolean isValid = numberOfServices() >= minimumNumberOfServices();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    services.add(aService);
    if (aService.indexOfAppointment(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aService.addAppointment(this);
      if (!wasAdded)
      {
        services.remove(aService);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    if (!services.contains(aService))
    {
      return wasRemoved;
    }

    if (numberOfServices() <= minimumNumberOfServices())
    {
      return wasRemoved;
    }

    int oldIndex = services.indexOf(aService);
    services.remove(oldIndex);
    if (aService.indexOfAppointment(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aService.removeAppointment(this);
      if (!wasRemoved)
      {
        services.add(oldIndex,aService);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setServices(Service... newServices)
  {
    boolean wasSet = false;
    ArrayList<Service> verifiedServices = new ArrayList<Service>();
    for (Service aService : newServices)
    {
      if (verifiedServices.contains(aService))
      {
        continue;
      }
      verifiedServices.add(aService);
    }

    if (verifiedServices.size() != newServices.length || verifiedServices.size() < minimumNumberOfServices())
    {
      return wasSet;
    }

    ArrayList<Service> oldServices = new ArrayList<Service>(services);
    services.clear();
    for (Service aNewService : verifiedServices)
    {
      services.add(aNewService);
      if (oldServices.contains(aNewService))
      {
        oldServices.remove(aNewService);
      }
      else
      {
        aNewService.addAppointment(this);
      }
    }

    for (Service anOldService : oldServices)
    {
      anOldService.removeAppointment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    if (aCustomer != null)
    {
      aCustomer.addAppointment(this);
    }
    wasSet = true;
    return wasSet;
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
      existingAppointmentManager.removeAppointment(this);
    }
    appointmentManager.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (technician != null)
    {
      Technician placeholderTechnician = technician;
      this.technician = null;
      placeholderTechnician.removeAppointment(this);
    }
    ArrayList<Service> copyOfServices = new ArrayList<Service>(services);
    services.clear();
    for(Service aService : copyOfServices)
    {
      aService.removeAppointment(this);
    }
    if (customer != null)
    {
      Customer placeholderCustomer = customer;
      this.customer = null;
      placeholderCustomer.removeAppointment(this);
    }
    AppointmentManager placeholderAppointmentManager = appointmentManager;
    this.appointmentManager = null;
    if(placeholderAppointmentManager != null)
    {
      placeholderAppointmentManager.removeAppointment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "technician = "+(getTechnician()!=null?Integer.toHexString(System.identityHashCode(getTechnician())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentManager = "+(getAppointmentManager()!=null?Integer.toHexString(System.identityHashCode(getAppointmentManager())):"null");
  }
}