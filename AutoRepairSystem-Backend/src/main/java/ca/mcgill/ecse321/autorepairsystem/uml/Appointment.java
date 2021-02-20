/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.sql.Time;
import java.sql.Date;

// line 50 "../../../../../AutoRepairSystem.ump"
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
  private ServiceCombo serviceCombo;
  private Customer customer;
  private AppointmentManager appointmentManager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Time aStartTime, Time aEndTime, Date aDate, ServiceCombo aServiceCombo, AppointmentManager aAppointmentManager)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    boolean didAddServiceCombo = setServiceCombo(aServiceCombo);
    if (!didAddServiceCombo)
    {
      throw new RuntimeException("Unable to create appointment due to serviceCombo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public ServiceCombo getServiceCombo()
  {
    return serviceCombo;
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
  /* Code from template association_SetOneToMany */
  public boolean setServiceCombo(ServiceCombo aServiceCombo)
  {
    boolean wasSet = false;
    if (aServiceCombo == null)
    {
      return wasSet;
    }

    ServiceCombo existingServiceCombo = serviceCombo;
    serviceCombo = aServiceCombo;
    if (existingServiceCombo != null && !existingServiceCombo.equals(aServiceCombo))
    {
      existingServiceCombo.removeAppointment(this);
    }
    serviceCombo.addAppointment(this);
    wasSet = true;
    return wasSet;
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
    ServiceCombo placeholderServiceCombo = serviceCombo;
    this.serviceCombo = null;
    if(placeholderServiceCombo != null)
    {
      placeholderServiceCombo.removeAppointment(this);
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
            "  " + "serviceCombo = "+(getServiceCombo()!=null?Integer.toHexString(System.identityHashCode(getServiceCombo())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentManager = "+(getAppointmentManager()!=null?Integer.toHexString(System.identityHashCode(getAppointmentManager())):"null");
  }
}