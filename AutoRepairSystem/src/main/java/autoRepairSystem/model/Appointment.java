/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.util.*;

// line 54 "../../AutoRepairSystem.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Associations
  private Technician technician;
  private TimeSlot timeSlot;
  private List<Service> services;
  private Customer customer;
  private AutoRepairSystem autoRepairSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Technician aTechnician, TimeSlot aTimeSlot, Customer aCustomer, AutoRepairSystem aAutoRepairSystem, Service... allServices)
  {
    boolean didAddTechnician = setTechnician(aTechnician);
    if (!didAddTechnician)
    {
      throw new RuntimeException("Unable to create appointment due to technician. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTimeSlot = setTimeSlot(aTimeSlot);
    if (!didAddTimeSlot)
    {
      throw new RuntimeException("Unable to create appointment due to timeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    services = new ArrayList<Service>();
    boolean didAddServices = setServices(allServices);
    if (!didAddServices)
    {
      throw new RuntimeException("Unable to create Appointment, must have at least 1 services. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAutoRepairSystem = setAutoRepairSystem(aAutoRepairSystem);
    if (!didAddAutoRepairSystem)
    {
      throw new RuntimeException("Unable to create appointment due to autoRepairSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Technician getTechnician()
  {
    return technician;
  }
  /* Code from template association_GetOne */
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
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
  /* Code from template association_GetOne */
  public AutoRepairSystem getAutoRepairSystem()
  {
    return autoRepairSystem;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTechnician(Technician aTechnician)
  {
    boolean wasSet = false;
    if (aTechnician == null)
    {
      return wasSet;
    }

    Technician existingTechnician = technician;
    technician = aTechnician;
    if (existingTechnician != null && !existingTechnician.equals(aTechnician))
    {
      existingTechnician.removeAppointment(this);
    }
    technician.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot == null)
    {
      //Unable to setTimeSlot to null, as appointment must always be associated to a timeSlot
      return wasSet;
    }
    
    Appointment existingAppointment = aNewTimeSlot.getAppointment();
    if (existingAppointment != null && !equals(existingAppointment))
    {
      //Unable to setTimeSlot, the current timeSlot already has a appointment, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    TimeSlot anOldTimeSlot = timeSlot;
    timeSlot = aNewTimeSlot;
    timeSlot.setAppointment(this);

    if (anOldTimeSlot != null)
    {
      anOldTimeSlot.setAppointment(null);
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
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    customer.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAutoRepairSystem(AutoRepairSystem aAutoRepairSystem)
  {
    boolean wasSet = false;
    if (aAutoRepairSystem == null)
    {
      return wasSet;
    }

    AutoRepairSystem existingAutoRepairSystem = autoRepairSystem;
    autoRepairSystem = aAutoRepairSystem;
    if (existingAutoRepairSystem != null && !existingAutoRepairSystem.equals(aAutoRepairSystem))
    {
      existingAutoRepairSystem.removeAppointment(this);
    }
    autoRepairSystem.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Technician placeholderTechnician = technician;
    this.technician = null;
    if(placeholderTechnician != null)
    {
      placeholderTechnician.removeAppointment(this);
    }
    TimeSlot existingTimeSlot = timeSlot;
    timeSlot = null;
    if (existingTimeSlot != null)
    {
      existingTimeSlot.setAppointment(null);
    }
    ArrayList<Service> copyOfServices = new ArrayList<Service>(services);
    services.clear();
    for(Service aService : copyOfServices)
    {
      aService.removeAppointment(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppointment(this);
    }
    AutoRepairSystem placeholderAutoRepairSystem = autoRepairSystem;
    this.autoRepairSystem = null;
    if(placeholderAutoRepairSystem != null)
    {
      placeholderAutoRepairSystem.removeAppointment(this);
    }
  }

}