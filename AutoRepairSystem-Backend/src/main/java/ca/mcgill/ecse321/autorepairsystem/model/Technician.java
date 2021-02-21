/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.util.*;
import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

// line 29 "../../../../../AutoRepairSystem.ump"
@Entity
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
  private List<TechnicianHour> technicianHours;
  
  @OneToMany
  public List<TechnicianHour> getTechnicianHours()
  {
    List<TechnicianHour> newTechnicianHours = Collections.unmodifiableList(technicianHours);
    return newTechnicianHours;
  }
  
  public void setTechnicianHours(List<TechnicianHour> newTechnicianHours) {
	  this.technicianHours = newTechnicianHours;
  }
  
  private List<Appointment> appointments;
  
  @OneToMany
  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }
  
  public void setAppointments(List<Appointment> newAppointments) {
	  this.appointments = newAppointments;
  }
  
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Technician(String aUsername, String aPassword, String aName, String aEmail, AppointmentManager aAppointmentManager)
  {
    super(aUsername, aPassword, aName, aEmail, aAppointmentManager);
    id = nextId++;
    technicianHours = new ArrayList<TechnicianHour>();
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
  public TechnicianHour getTechnicianHour(int index)
  {
    TechnicianHour aTechnicianHour = technicianHours.get(index);
    return aTechnicianHour;
  }


  public int numberOfTechnicianHours()
  {
    int number = technicianHours.size();
    return number;
  }

  public boolean hasTechnicianHours()
  {
    boolean has = technicianHours.size() > 0;
    return has;
  }

  public int indexOfTechnicianHour(TechnicianHour aTechnicianHour)
  {
    int index = technicianHours.indexOf(aTechnicianHour);
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
  public static int minimumNumberOfTechnicianHours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TechnicianHour addTechnicianHour(Time aStartTime, Time aEndTime, Date aDate)
  {
    return new TechnicianHour(aStartTime, aEndTime, aDate, this);
  }

  public boolean addTechnicianHour(TechnicianHour aTechnicianHour)
  {
    boolean wasAdded = false;
    if (technicianHours.contains(aTechnicianHour)) { return false; }
    Technician existingTechnician = aTechnicianHour.getTechnician();
    boolean isNewTechnician = existingTechnician != null && !this.equals(existingTechnician);
    if (isNewTechnician)
    {
      aTechnicianHour.setTechnician(this);
    }
    else
    {
      technicianHours.add(aTechnicianHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTechnicianHour(TechnicianHour aTechnicianHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aTechnicianHour, as it must always have a technician
    if (!this.equals(aTechnicianHour.getTechnician()))
    {
      technicianHours.remove(aTechnicianHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTechnicianHourAt(TechnicianHour aTechnicianHour, int index)
  {  
    boolean wasAdded = false;
    if(addTechnicianHour(aTechnicianHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTechnicianHours()) { index = numberOfTechnicianHours() - 1; }
      technicianHours.remove(aTechnicianHour);
      technicianHours.add(index, aTechnicianHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTechnicianHourAt(TechnicianHour aTechnicianHour, int index)
  {
    boolean wasAdded = false;
    if(technicianHours.contains(aTechnicianHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTechnicianHours()) { index = numberOfTechnicianHours() - 1; }
      technicianHours.remove(aTechnicianHour);
      technicianHours.add(index, aTechnicianHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTechnicianHourAt(aTechnicianHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Time aStartTime, Time aEndTime, Date aDate, Customer aCustomer, AppointmentManager aAppointmentManager)
  {
    return new Appointment(aStartTime, aEndTime, aDate, this, aCustomer, aAppointmentManager);
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
    while (technicianHours.size() > 0)
    {
      TechnicianHour aTechnicianHour = technicianHours.get(technicianHours.size() - 1);
      aTechnicianHour.delete();
      technicianHours.remove(aTechnicianHour);
    }
    
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]";
  }
}