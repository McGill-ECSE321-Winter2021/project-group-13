/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.util.*;

// line 15 "../../../../../AutoRepairSystem.ump"
public class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String name;
  private int duration;
  private int price;

  //Service Associations
  private AppointmentManager appointmentManager;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aName, int aDuration, int aPrice, AppointmentManager aAppointmentManager)
  {
    name = aName;
    duration = aDuration;
    price = aPrice;
    boolean didAddAppointmentManager = setAppointmentManager(aAppointmentManager);
    if (!didAddAppointmentManager)
    {
      throw new RuntimeException("Unable to create service due to appointmentManager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getDuration()
  {
    return duration;
  }

  public int getPrice()
  {
    return price;
  }
  /* Code from template association_GetOne */
  public AppointmentManager getAppointmentManager()
  {
    return appointmentManager;
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
      existingAppointmentManager.removeService(this);
    }
    appointmentManager.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    appointments.add(aAppointment);
    if (aAppointment.indexOfService(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAppointment.addService(this);
      if (!wasAdded)
      {
        appointments.remove(aAppointment);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    if (!appointments.contains(aAppointment))
    {
      return wasRemoved;
    }

    int oldIndex = appointments.indexOf(aAppointment);
    appointments.remove(oldIndex);
    if (aAppointment.indexOfService(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAppointment.removeService(this);
      if (!wasRemoved)
      {
        appointments.add(oldIndex,aAppointment);
      }
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
    AppointmentManager placeholderAppointmentManager = appointmentManager;
    this.appointmentManager = null;
    if(placeholderAppointmentManager != null)
    {
      placeholderAppointmentManager.removeService(this);
    }
    ArrayList<Appointment> copyOfAppointments = new ArrayList<Appointment>(appointments);
    appointments.clear();
    for(Appointment aAppointment : copyOfAppointments)
    {
      if (aAppointment.numberOfServices() <= Appointment.minimumNumberOfServices())
      {
        aAppointment.delete();
      }
      else
      {
        aAppointment.removeService(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "duration" + ":" + getDuration()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentManager = "+(getAppointmentManager()!=null?Integer.toHexString(System.identityHashCode(getAppointmentManager())):"null");
  }
}