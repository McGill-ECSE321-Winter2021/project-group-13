/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.util.*;

// line 31 "../../../../../AutoRepairSystem.ump"
public class Technician extends User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int id;

  //Technician Associations
  private List<AvailabilitySchedule> workHour;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Technician(String aUsername, String aPassword, String aName, String aEmail, AppointmentManager aAppointmentManager)
  {
    super(aUsername, aPassword, aName, aEmail, aAppointmentManager);
    id = nextId++;
    workHour = new ArrayList<AvailabilitySchedule>();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public AvailabilitySchedule getWorkHour(int index)
  {
    AvailabilitySchedule aWorkHour = workHour.get(index);
    return aWorkHour;
  }

  public List<AvailabilitySchedule> getWorkHour()
  {
    List<AvailabilitySchedule> newWorkHour = Collections.unmodifiableList(workHour);
    return newWorkHour;
  }

  public int numberOfWorkHour()
  {
    int number = workHour.size();
    return number;
  }

  public boolean hasWorkHour()
  {
    boolean has = workHour.size() > 0;
    return has;
  }

  public int indexOfWorkHour(AvailabilitySchedule aWorkHour)
  {
    int index = workHour.indexOf(aWorkHour);
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
  public static int minimumNumberOfWorkHour()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addWorkHour(AvailabilitySchedule aWorkHour)
  {
    boolean wasAdded = false;
    if (workHour.contains(aWorkHour)) { return false; }
    Technician existingTechnician = aWorkHour.getTechnician();
    if (existingTechnician == null)
    {
      aWorkHour.setTechnician(this);
    }
    else if (!this.equals(existingTechnician))
    {
      existingTechnician.removeWorkHour(aWorkHour);
      addWorkHour(aWorkHour);
    }
    else
    {
      workHour.add(aWorkHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkHour(AvailabilitySchedule aWorkHour)
  {
    boolean wasRemoved = false;
    if (workHour.contains(aWorkHour))
    {
      workHour.remove(aWorkHour);
      aWorkHour.setTechnician(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkHourAt(AvailabilitySchedule aWorkHour, int index)
  {  
    boolean wasAdded = false;
    if(addWorkHour(aWorkHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkHour()) { index = numberOfWorkHour() - 1; }
      workHour.remove(aWorkHour);
      workHour.add(index, aWorkHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkHourAt(AvailabilitySchedule aWorkHour, int index)
  {
    boolean wasAdded = false;
    if(workHour.contains(aWorkHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkHour()) { index = numberOfWorkHour() - 1; }
      workHour.remove(aWorkHour);
      workHour.add(index, aWorkHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkHourAt(aWorkHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    Technician existingTechnician = aAppointment.getTechnician();
    if (existingTechnician == null)
    {
      aAppointment.setTechnician(this);
    }
    else if (!this.equals(existingTechnician))
    {
      existingTechnician.removeAppointment(aAppointment);
      addAppointment(aAppointment);
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
    if (appointments.contains(aAppointment))
    {
      appointments.remove(aAppointment);
      aAppointment.setTechnician(null);
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
    while (workHour.size() > 0)
    {
      AvailabilitySchedule aWorkHour = workHour.get(workHour.size() - 1);
      aWorkHour.delete();
      workHour.remove(aWorkHour);
    }
    
    while( !appointments.isEmpty() )
    {
      appointments.get(0).setTechnician(null);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]";
  }
}