/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

// line 49 "../../../../../AutoRepairSystem.ump"
public class TechnicianHour extends WorkHour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TechnicianHour Associations
  private Technician technician;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TechnicianHour(Time aStartTime, Time aEndTime, Date aDate, Technician aTechnician)
  {
    super(aStartTime, aEndTime, aDate);
    boolean didAddTechnician = setTechnician(aTechnician);
    if (!didAddTechnician)
    {
      throw new RuntimeException("Unable to create technicianHour due to technician. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      existingTechnician.removeTechnicianHour(this);
    }
    technician.addTechnicianHour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Technician placeholderTechnician = technician;
    this.technician = null;
    if(placeholderTechnician != null)
    {
      placeholderTechnician.removeTechnicianHour(this);
    }
    super.delete();
  }

}