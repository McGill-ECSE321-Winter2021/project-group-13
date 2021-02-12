/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package autoRepairSystem.model;

// line 32 "../../AutoRepairSystem.ump"
public abstract class User
{

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
    username = aUsername;
    password = aPassword;
    name = aName;
    email = aEmail;
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
    username = aUsername;
    wasSet = true;
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
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
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