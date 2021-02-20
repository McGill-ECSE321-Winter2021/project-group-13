/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 6 "../../../../../AutoRepairSystem.ump"
public class AppointmentManager
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AppointmentManager Associations
  private List<AvailabilitySchedule> businessHour;
  private List<Service> services;
  private List<User> users;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AppointmentManager()
  {
    businessHour = new ArrayList<AvailabilitySchedule>();
    services = new ArrayList<Service>();
    users = new ArrayList<User>();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public AvailabilitySchedule getBusinessHour(int index)
  {
    AvailabilitySchedule aBusinessHour = businessHour.get(index);
    return aBusinessHour;
  }

  public List<AvailabilitySchedule> getBusinessHour()
  {
    List<AvailabilitySchedule> newBusinessHour = Collections.unmodifiableList(businessHour);
    return newBusinessHour;
  }

  public int numberOfBusinessHour()
  {
    int number = businessHour.size();
    return number;
  }

  public boolean hasBusinessHour()
  {
    boolean has = businessHour.size() > 0;
    return has;
  }

  public int indexOfBusinessHour(AvailabilitySchedule aBusinessHour)
  {
    int index = businessHour.indexOf(aBusinessHour);
    return index;
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
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBusinessHour()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addBusinessHour(AvailabilitySchedule aBusinessHour)
  {
    boolean wasAdded = false;
    if (businessHour.contains(aBusinessHour)) { return false; }
    AppointmentManager existingAppointmentManager = aBusinessHour.getAppointmentManager();
    if (existingAppointmentManager == null)
    {
      aBusinessHour.setAppointmentManager(this);
    }
    else if (!this.equals(existingAppointmentManager))
    {
      existingAppointmentManager.removeBusinessHour(aBusinessHour);
      addBusinessHour(aBusinessHour);
    }
    else
    {
      businessHour.add(aBusinessHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusinessHour(AvailabilitySchedule aBusinessHour)
  {
    boolean wasRemoved = false;
    if (businessHour.contains(aBusinessHour))
    {
      businessHour.remove(aBusinessHour);
      aBusinessHour.setAppointmentManager(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessHourAt(AvailabilitySchedule aBusinessHour, int index)
  {  
    boolean wasAdded = false;
    if(addBusinessHour(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHour()) { index = numberOfBusinessHour() - 1; }
      businessHour.remove(aBusinessHour);
      businessHour.add(index, aBusinessHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessHourAt(AvailabilitySchedule aBusinessHour, int index)
  {
    boolean wasAdded = false;
    if(businessHour.contains(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHour()) { index = numberOfBusinessHour() - 1; }
      businessHour.remove(aBusinessHour);
      businessHour.add(index, aBusinessHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessHourAt(aBusinessHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, int aDuration, int aPrice)
  {
    return new Service(aName, aDuration, aPrice, this);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    AppointmentManager existingAppointmentManager = aService.getAppointmentManager();
    boolean isNewAppointmentManager = existingAppointmentManager != null && !this.equals(existingAppointmentManager);
    if (isNewAppointmentManager)
    {
      aService.setAppointmentManager(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a appointmentManager
    if (!this.equals(aService.getAppointmentManager()))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    AppointmentManager existingAppointmentManager = aUser.getAppointmentManager();
    boolean isNewAppointmentManager = existingAppointmentManager != null && !this.equals(existingAppointmentManager);
    if (isNewAppointmentManager)
    {
      aUser.setAppointmentManager(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a appointmentManager
    if (!this.equals(aUser.getAppointmentManager()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Time aStartTime, Time aEndTime, Date aDate, ServiceCombo aServiceCombo)
  {
    return new Appointment(aStartTime, aEndTime, aDate, aServiceCombo, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    AppointmentManager existingAppointmentManager = aAppointment.getAppointmentManager();
    boolean isNewAppointmentManager = existingAppointmentManager != null && !this.equals(existingAppointmentManager);
    if (isNewAppointmentManager)
    {
      aAppointment.setAppointmentManager(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a appointmentManager
    if (!this.equals(aAppointment.getAppointmentManager()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (businessHour.size() > 0)
    {
      AvailabilitySchedule aBusinessHour = businessHour.get(businessHour.size() - 1);
      aBusinessHour.delete();
      businessHour.remove(aBusinessHour);
    }
    
    while (services.size() > 0)
    {
      Service aService = services.get(services.size() - 1);
      aService.delete();
      services.remove(aService);
    }
    
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (appointments.size() > 0)
    {
      Appointment aAppointment = appointments.get(appointments.size() - 1);
      aAppointment.delete();
      appointments.remove(aAppointment);
    }
    
  }

}