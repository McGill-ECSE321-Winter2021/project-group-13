/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.util.*;
import javax.persistence.*;

// line 38 "../../../../../AutoRepairSystem.ump"
@Entity
public class Administrator extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Administrator(String aUsername, String aPassword, String aName, String aEmail, AppointmentManager aAppointmentManager)
  {
    super(aUsername, aPassword, aName, aEmail, aAppointmentManager);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}