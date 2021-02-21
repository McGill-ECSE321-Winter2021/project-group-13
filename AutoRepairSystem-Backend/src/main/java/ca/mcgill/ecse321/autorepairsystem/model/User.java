/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.model;
import java.util.*;
import javax.persistence.*;

// line 20 "../../../../../AutoRepairSystem.ump"
@Entity
public abstract class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByUsername = new HashMap<String, User>();
  private static Map<String, User> usersByEmail = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  
  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
      return true;
    }
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      usersByUsername.remove(anOldUsername);
    }
    usersByUsername.put(aUsername, this);
    return wasSet;
  }

  @Id
  public String getUsername()
  {
    return username;
  }
  
  private String password;
  
  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }
  
  public String getPassword()
  {
    return password;
  }
  
  private String name;
  
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }
  
  public String getName()
  {
    return name;
  }
  
  private String email;
  
  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    String anOldEmail = getEmail();
    if (anOldEmail != null && anOldEmail.equals(aEmail)) {
      return true;
    }
    if (hasWithEmail(aEmail)) {
      return wasSet;
    }
    email = aEmail;
    wasSet = true;
    if (anOldEmail != null) {
      usersByEmail.remove(anOldEmail);
    }
    usersByEmail.put(aEmail, this);
    return wasSet;
  }
  
  public String getEmail()
  {
    return email;
  }

  //User Associations
  private AppointmentManager appointmentManager;
  
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
      existingAppointmentManager.removeUser(this);
    }
    appointmentManager.addUser(this);
    wasSet = true;
    return wasSet;
  }
  
  @ManyToOne
  public AppointmentManager getAppointmentManager()
  {
    return appointmentManager;
  }
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aPassword, String aName, String aEmail, AppointmentManager aAppointmentManager)
  {
    password = aPassword;
    name = aName;
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAppointmentManager = setAppointmentManager(aAppointmentManager);
    if (!didAddAppointmentManager)
    {
      throw new RuntimeException("Unable to create user due to appointmentManager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------


  /* Code from template attribute_GetUnique */
  public static User getWithUsername(String aUsername)
  {
    return usersByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  /* Code from template attribute_GetUnique */
  public static User getWithEmail(String aEmail)
  {
    return usersByEmail.get(aEmail);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmail(String aEmail)
  {
    return getWithEmail(aEmail) != null;
  }
  /* Code from template association_GetOne */
  /* Code from template association_SetOneToMany */

  public void delete()
  {
    usersByUsername.remove(getUsername());
    usersByEmail.remove(getEmail());
    AppointmentManager placeholderAppointmentManager = appointmentManager;
    this.appointmentManager = null;
    if(placeholderAppointmentManager != null)
    {
      placeholderAppointmentManager.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentManager = "+(getAppointmentManager()!=null?Integer.toHexString(System.identityHashCode(getAppointmentManager())):"null");
  }
}