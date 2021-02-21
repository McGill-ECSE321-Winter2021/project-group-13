/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.*;

// line 4 "../../../../../AutoRepairSystem.ump"
@Entity
public class AppointmentManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	
	private Integer id;
	  
	  @Id
	  public Integer getId() {
		  return this.id;
	  }
	  
	  public void setId(Integer newId) {
		  this.id = newId;
	  }

  //AppointmentManager Associations
  private List<Business> businesses;
  
  @OneToMany(cascade= {CascadeType.ALL})
  public List<Business> getBusinesses()
  {
    List<Business> newBusinesses = Collections.unmodifiableList(businesses);
    return newBusinesses;
  }

  public void setBusinesses(List<Business> newBusinesses) {
	  this.businesses = newBusinesses;
  }
  
  private List<Service> services;
  
  @OneToMany(cascade= {CascadeType.ALL})
  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }
  
  public void setServices(List<Service> services) {
	  this.services = services; 
  }
  
  private List<User> users;
  
  @OneToMany(cascade= {CascadeType.ALL})
  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }
  
  public void setUsers(List<User> users) {
	  this.users = users; 
  }
  	
  private List<Appointment> appointments;
  
  @OneToMany(cascade= {CascadeType.ALL})
  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }
  
  public void setAppointments(List<Appointment> appointments) {
	  this.appointments = appointments; 
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AppointmentManager()
  {
    businesses = new ArrayList<Business>();
    services = new ArrayList<Service>();
    users = new ArrayList<User>();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Business getBusiness(int index)
  {
    Business aBusiness = businesses.get(index);
    return aBusiness;
  }


  public int numberOfBusinesses()
  {
    int number = businesses.size();
    return number;
  }

  public boolean hasBusinesses()
  {
    boolean has = businesses.size() > 0;
    return has;
  }

  public int indexOfBusiness(Business aBusiness)
  {
    int index = businesses.indexOf(aBusiness);
    return index;
  }
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
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
  public static int minimumNumberOfBusinesses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Business addBusiness()
  {
    return new Business(this);
  }

  public boolean addBusiness(Business aBusiness)
  {
    boolean wasAdded = false;
    if (businesses.contains(aBusiness)) { return false; }
    AppointmentManager existingAppointmentManager = aBusiness.getAppointmentManager();
    boolean isNewAppointmentManager = existingAppointmentManager != null && !this.equals(existingAppointmentManager);
    if (isNewAppointmentManager)
    {
      aBusiness.setAppointmentManager(this);
    }
    else
    {
      businesses.add(aBusiness);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusiness(Business aBusiness)
  {
    boolean wasRemoved = false;
    //Unable to remove aBusiness, as it must always have a appointmentManager
    if (!this.equals(aBusiness.getAppointmentManager()))
    {
      businesses.remove(aBusiness);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessAt(Business aBusiness, int index)
  {  
    boolean wasAdded = false;
    if(addBusiness(aBusiness))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinesses()) { index = numberOfBusinesses() - 1; }
      businesses.remove(aBusiness);
      businesses.add(index, aBusiness);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessAt(Business aBusiness, int index)
  {
    boolean wasAdded = false;
    if(businesses.contains(aBusiness))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinesses()) { index = numberOfBusinesses() - 1; }
      businesses.remove(aBusiness);
      businesses.add(index, aBusiness);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessAt(aBusiness, index);
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
  public Appointment addAppointment(Time aStartTime, Time aEndTime, Date aDate, Technician aTechnician, Customer aCustomer)
  {
    return new Appointment(aStartTime, aEndTime, aDate, aTechnician, aCustomer, this);
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
    while (businesses.size() > 0)
    {
      Business aBusiness = businesses.get(businesses.size() - 1);
      aBusiness.delete();
      businesses.remove(aBusiness);
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