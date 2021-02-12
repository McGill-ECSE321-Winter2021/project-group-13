/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.util.*;

// line 41 "../../AutoRepairSystem.ump"
public class Technician extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Technician Associations
  private List<Appointment> appointments;
  private Schedule schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Technician(String aUsername, String aPassword, String aName, String aEmail, AutoRepairSystem aAutoRepairSystem, Schedule aSchedule)
  {
    super(aUsername, aPassword, aName, aEmail, aAutoRepairSystem);
    appointments = new ArrayList<Appointment>();
    if (aSchedule == null || aSchedule.getTechnician() != null)
    {
      throw new RuntimeException("Unable to create Technician due to aSchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    schedule = aSchedule;
  }

  public Technician(String aUsername, String aPassword, String aName, String aEmail, AutoRepairSystem aAutoRepairSystem)
  {
    super(aUsername, aPassword, aName, aEmail, aAutoRepairSystem);
    appointments = new ArrayList<Appointment>();
    schedule = new Schedule(this);
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetOne */
  public Schedule getSchedule()
  {
    return schedule;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(TimeSlot aTimeSlot, Customer aCustomer, AutoRepairSystem aAutoRepairSystem, Service... allServices)
  {
    return new Appointment(this, aTimeSlot, aCustomer, aAutoRepairSystem, allServices);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    Technician existingTechnician = aAppointment.getTechnician();
    boolean isNewTechnician = existingTechnician != null && !this.equals(existingTechnician);
    if (isNewTechnician)
    {
      aAppointment.setTechnician(this);
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
    //Unable to remove aAppointment, as it must always have a technician
    if (!this.equals(aAppointment.getTechnician()))
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
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    Schedule existingSchedule = schedule;
    schedule = null;
    if (existingSchedule != null)
    {
      existingSchedule.delete();
    }
    super.delete();
  }

}