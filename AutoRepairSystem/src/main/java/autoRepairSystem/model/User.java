/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;
import java.util.*;

// line 25 "../../AutoRepairSystem.ump"
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
  private String password;
  private String name;
  private String email;

  //User Associations
  private AutoRepairSystem autoRepairSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aPassword, String aName, String aEmail, AutoRepairSystem aAutoRepairSystem)
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
    boolean didAddAutoRepairSystem = setAutoRepairSystem(aAutoRepairSystem);
    if (!didAddAutoRepairSystem)
    {
      throw new RuntimeException("Unable to create user due to autoRepairSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

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

  public String getUsername()
  {
    return username;
  }
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

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
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
  public AutoRepairSystem getAutoRepairSystem()
  {
    return autoRepairSystem;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAutoRepairSystem(AutoRepairSystem aAutoRepairSystem)
  {
    boolean wasSet = false;
    if (aAutoRepairSystem == null)
    {
      return wasSet;
    }

    AutoRepairSystem existingAutoRepairSystem = autoRepairSystem;
    autoRepairSystem = aAutoRepairSystem;
    if (existingAutoRepairSystem != null && !existingAutoRepairSystem.equals(aAutoRepairSystem))
    {
      existingAutoRepairSystem.removeUser(this);
    }
    autoRepairSystem.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usersByUsername.remove(getUsername());
    usersByEmail.remove(getEmail());
    AutoRepairSystem placeholderAutoRepairSystem = autoRepairSystem;
    this.autoRepairSystem = null;
    if(placeholderAutoRepairSystem != null)
    {
      placeholderAutoRepairSystem.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "autoRepairSystem = "+(getAutoRepairSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairSystem())):"null");
  }
}