/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autorepairsystem.uml;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

// line 54 "../../../../../AutoRepairSystem.ump"
public class BusinessHour extends WorkHour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BusinessHour Associations
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BusinessHour(Time aStartTime, Time aEndTime, Date aDate, Business aBusiness)
  {
    super(aStartTime, aEndTime, aDate);
    boolean didAddBusiness = setBusiness(aBusiness);
    if (!didAddBusiness)
    {
      throw new RuntimeException("Unable to create businessHour due to business. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBusiness(Business aBusiness)
  {
    boolean wasSet = false;
    if (aBusiness == null)
    {
      return wasSet;
    }

    Business existingBusiness = business;
    business = aBusiness;
    if (existingBusiness != null && !existingBusiness.equals(aBusiness))
    {
      existingBusiness.removeBusinessHour(this);
    }
    business.addBusinessHour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Business placeholderBusiness = business;
    this.business = null;
    if(placeholderBusiness != null)
    {
      placeholderBusiness.removeBusinessHour(this);
    }
    super.delete();
  }

}