package ca.mcgill.ecse321.autorepairsystem.service;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autorepairsystem.dao.*;
import ca.mcgill.ecse321.autorepairsystem.model.*;

@Service
public class AutoRepairSystemService {

  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  EndUserRepository endUserRepository;
  @Autowired
  AdministratorRepository administratorRepository;
  @Autowired
  TechnicianRepository technicianRepository;
  @Autowired
  TechnicianHourRepository technicianHourRepository;
  @Autowired
  AppointmentRepository appointmentRepository;
  @Autowired
  WorkItemRepository workItemRepository;
  @Autowired
  WorkHourRepository workHourRepository;
  @Autowired
  WorkBreakRepository workBreakRepository;
  @Autowired
  BusinessHourRepository businessHourRepository;
  
  
  /**
  * A method which takes no input and is used to find all of the appointments which exist in the database
  * 
  * @return A list of all appointments in the database.
  */
  @Transactional
  public List<Appointment> getAllAppointments() {

    return toList(appointmentRepository.findAll());
  }
  
  /**
  * A method which takes a customer as input and returns all of the appointments associated with that customer in the database.
  * 
  * @param customer; A specific customer object whose appointments we desire 
  * @return A list of all appointments associated with a specific customer
  */
  @Transactional
  public List<Appointment> getAppointmentsByCustomer(Customer customer) throws IllegalArgumentException {

    if (customer == null) {
      throw new IllegalArgumentException("Invalid customer");
    }

    try {
      getCustomerByUsername(customer.getUsername());
    } catch (Exception e) {
      throw new IllegalArgumentException("Customer does not exist");
    }

    List<Appointment> appointments =
        new ArrayList<Appointment>(appointmentRepository.findAppointmentByCustomer(customer));

    return appointments;
  }
  
  
  /**
   * A method which takes a date as input and returns all of the appointments associated with that date in the database.
   * 
   * @param date; The specific java.sql.date object in question which has appointments occurring on that day which we desire.
   * @return A list of all appointments occurring on that date
   */
  @Transactional
  public List<Appointment> getAppointmentsByDate(java.sql.Date date) {

    List<Appointment> appointments = new ArrayList<Appointment>(appointmentRepository.findAppointmentByDate(date));

    return appointments;
  }
  
  
  /**
   * A method which takes a technician as input and returns all of the appointments associated with that technician in the database.
   * 
   * @param technician; A specific technician object whose appointments we desire (they are working during these appointments)
   * @return A list of all appointments associated with a specific technician
   */
  @Transactional
  public List<Appointment> getAppointmentsByTechnician(Technician technician) throws IllegalArgumentException {

    if (technician == null) {
      throw new IllegalArgumentException("Invalid technician");
    }

    try {
      getTechnicianByUsername(technician.getUsername());
    } catch (Exception e) {
      throw new IllegalArgumentException("Technician does not exist");
    }

    List<Appointment> appointments =
        new ArrayList<Appointment>(appointmentRepository.findAppointmentByTechnician(technician));

    return appointments;
  }

  // add update appointment method
  /**
   * A method which creates an appointment and automatically assigns a technician to work on it.
   * 
   * @param services; A set of services that the customer wants to have done on their car 
   * @param customer; A specific customer object which is the singular client of that appointment
   * @param starTime; A java.sql.Time object, denoting the start time when the client wants their appointment
   * @param date; A java.sql.Date object which represents the day the appointment will occur
   * @return The specific appointment that was just created
   */
  @Transactional
  public Appointment createAppointmentAnyTechnician(Set<WorkItem> services, Customer customer, 
      java.sql.Time startTime, java.sql.Date date) throws IllegalArgumentException {

      if (services == null || services.size() == 0) {
        throw new IllegalArgumentException("No services specified");
      }

      if (customer == null) {
        throw new IllegalArgumentException("Invalid customer");
      }
      
      if (startTime == null || date == null) {
        throw new IllegalArgumentException("Invalid date and time");
      }

      try {
        getCustomerByUsername(customer.getUsername());
      } catch (Exception e) {
        throw new IllegalArgumentException("Customer does not exist");
      }

      // sum up service durations
      int sumMin = 0;
      int sumPrice = 0;
      for (WorkItem s : services) {
        sumMin += s.getDuration();
        sumPrice += s.getPrice();
      }

      // set endTime
      Calendar cal = Calendar.getInstance();
      cal.setTime(startTime);
      cal.add(Calendar.MINUTE, sumMin);

      java.sql.Time endTime = new java.sql.Time(cal.getTimeInMillis());

      Map<Technician, List<TechnicianHour>> availabilities = getTechnicianAvailableTechnicianHoursByDate(date, sumMin);
      
      Technician availableTechnician = null;

      boolean available = false;
      for (Technician technician : availabilities.keySet()) {
        
        List<TechnicianHour> availabilitiesForTechnician = availabilities.get(technician);
        
        for (TechnicianHour h : availabilitiesForTechnician) {
          available = available | (!h.getStartTime().after(startTime) && !h.getEndTime().before(endTime));
          
          if (available) {
            availableTechnician = technician;
            break;
          }
        }
        
        if (available) {
          break;
        }
      }

      if (!available) {
        throw new IllegalArgumentException("Technician not available during specified time");
      }

      Appointment appointment = new Appointment();
      appointment.setStartTime(startTime);
      appointment.setEndTime(endTime);
      appointment.setTechnician(availableTechnician);
      appointment.setWorkItem(services);
      appointment.setCustomer(customer);
      appointment.setDate(date);

      customer.setAmountOwed(customer.getAmountOwed() + sumPrice);

      appointmentRepository.save(appointment);

      return appointment;
  }

  /**
   * A method which creates an appointment and necessitates all "appointment" fields to be provided as arguments
   * 
   * @param services; A set of workItems that the customer wants to have done on their car 
   * @param customer; A specific customer object which is the singular client of that appointment
   * @param technician; A specific technician that will be working on the appointment
   * @param starTime; A java.sql.Time object, denoting the start time when the client wants their appointment
   * @param date; A java.sql.Date object which represents the day the appointment will occur
   * @return The specific appointment that was just created
   */
  @Transactional
  public Appointment createAppointment(Set<WorkItem> services, Customer customer, Technician technician,
      java.sql.Time startTime, java.sql.Date date) throws IllegalArgumentException {

    if (services == null || services.size() == 0) {
      throw new IllegalArgumentException("No services specified");
    }

    if (customer == null) {
      throw new IllegalArgumentException("Invalid customer");
    }

    if (technician == null) {
      throw new IllegalArgumentException("Invalid technician");
    }

    if (startTime == null || date == null) {
      throw new IllegalArgumentException("Invalid date and time");
    }

    try {
      getCustomerByUsername(customer.getUsername());
    } catch (Exception e) {
      throw new IllegalArgumentException("Customer does not exist");
    }

    try {
      getTechnicianByUsername(technician.getUsername());
    } catch (Exception e) {
      throw new IllegalArgumentException("Technician does not exist");
    }

    // sum up service durations
    int sumMin = 0;
    int sumPrice = 0;
    for (WorkItem s : services) {
      sumMin += s.getDuration();
      sumPrice += s.getPrice();
    }

    // set endTime
    Calendar cal = Calendar.getInstance();
    cal.setTime(startTime);
    cal.add(Calendar.MINUTE, sumMin);

    java.sql.Time endTime = new java.sql.Time(cal.getTimeInMillis());

    Map<Technician, List<TechnicianHour>> availabilities = getTechnicianAvailableTechnicianHoursByDate(date, sumMin);
    List<TechnicianHour> availabilitiesForTechnician = availabilities.get(technician);

    boolean available = false;
    for (TechnicianHour h : availabilitiesForTechnician) {
      available = available | (!h.getStartTime().after(startTime) && !h.getEndTime().before(endTime));
    }

    if (!available) {
      throw new IllegalArgumentException("Technician not available during specified time");
    }

    Appointment appointment = new Appointment();
    appointment.setStartTime(startTime);
    appointment.setEndTime(endTime);
    appointment.setTechnician(technician);
    appointment.setWorkItem(services);
    appointment.setCustomer(customer);
    appointment.setDate(date);

    customer.setAmountOwed(customer.getAmountOwed() + sumPrice);

    appointmentRepository.save(appointment);

    return appointment;
  }

  /**
   * A method which updates the info/details of an appointment
   * 
   * @param id; the primary key of the appointment you want to update
   * @param technician; A new/updated technician that will be working on the appointment
   * @param services; A set of workItems that will be the new services performed during the appointment
   * @param starTime; A java.sql.Time object, denoting the new start time when the client wants their appointment
   * @param date; A java.sql.Date object which represents the new day the appointment will occur
   * @return The newly updated appointment that was just altered 
   */
  @Transactional
  public Appointment updateAppointment(Integer id, Technician technician, Set<WorkItem> services,
      java.sql.Time startTime, java.sql.Date date) throws IllegalArgumentException {

    if (services == null || services.size() == 0) {
      throw new IllegalArgumentException("No services specified");
    }

    if (technician == null) {
      throw new IllegalArgumentException("Invalid technician");
    }

    if (startTime == null || date == null) {
      throw new IllegalArgumentException("Invalid date and time");
    }

    Appointment deletedAppointment = null;

    try {
      deletedAppointment = deleteAppointment(id);
    } catch (Exception e) {
      throw new IllegalArgumentException("Appointment could not be found");
    }

    try {
      return createAppointment(services, deletedAppointment.getCustomer(), technician, startTime, date);
    } catch (Exception e) {
      // can't throw exception and return
      // -> check ID to check for change
      return createAppointment(deletedAppointment.getWorkItem(), deletedAppointment.getCustomer(),
          deletedAppointment.getTechnician(), deletedAppointment.getStartTime(), deletedAppointment.getDate());
    }

  }
  /**
   * A method which deletes an appointment
   * 
   * @param id; the primary key of the appointment you want to delete
   * @return The non existing appointment that was just deleted 
   */
  @Transactional
  public Appointment deleteAppointment(Integer id) throws IllegalArgumentException {

    if (id == null) {
      throw new IllegalArgumentException("Invalid ID");
    }

    Appointment appointment = appointmentRepository.findAppointmentById(id);

    if (appointment == null) {
      throw new IllegalArgumentException("Appointment does not exist");
    }

    int sumPrice = 0;
    for (WorkItem s : appointment.getWorkItem()) {
      sumPrice += s.getPrice();
    }

    appointment.getCustomer().setAmountOwed(appointment.getCustomer().getAmountOwed() - sumPrice);

    appointmentRepository.delete(appointment);
    return appointment;
  }

  /**
   * A method which returns a map of technicians that are available for a minimum amount of time on a given day.
   * 
   * @param date; The java.sql.Date object in question you are seeking to find technicians for
   * @param minDurationInMin; the minimum time in minutes that a technician must be available for (filtering criteria)
   * @return A map of technicians which are available for the specified minimum duration and their associated technicianHour schedule
   */
  @Transactional
  // Organized by technician
  // Filtered by minimum duration
  public Map<Technician, List<TechnicianHour>> getTechnicianAvailableTechnicianHoursByDate(java.sql.Date date,
      int minDurationInMin) {

    Map<Technician, List<TechnicianHour>> technicianHoursByDate = getTechnicianHoursByDate(date);
    Map<Technician, List<Appointment>> technicianAppointmentsByDate = getTechnicianAppointmentsByDate(date);

    for (Map.Entry<Technician, List<TechnicianHour>> entry : technicianHoursByDate.entrySet()) {

      List<TechnicianHour> processed = subtractWorkBreaksFromTechnicianHours(entry.getValue());
      List<Appointment> appointments = cleanupAppointments(technicianAppointmentsByDate.get(entry.getKey()));

      processed = subtractAppointmentsFromTechnicianHours(processed, appointments);
      processed = filterByMinDuration(processed, minDurationInMin);

      technicianHoursByDate.put(entry.getKey(), processed);
    }

    return technicianHoursByDate;

  }

  // helper methods for completing getTechnicianAvailableTechnicianHoursByDate(java.sql.Date date,int minDurationInMin)
  /*
   * Assumptions: -breaks are within technical hours time -appointments are within technical hours time -no overlap
   * between appointments -Service.getDuration() in minutes
   * 
   * Not assumed: -no overlap between breaks
   */
  private List<Appointment> cleanupAppointments(List<Appointment> appointments) {

    if (appointments == null || appointments.size() <= 1) {
      return appointments;
    }

    // sort
    List<Appointment> sorted = new ArrayList<Appointment>(appointments);
    Collections.sort(sorted, new Comparator<Appointment>() {
      @Override
      public int compare(Appointment o1, Appointment o2) {

        if (o1.getStartTime().before(o2.getStartTime())) {
          return -1;
        } else if (o1.getStartTime().after(o2.getStartTime())) {
          return 1;
        }

        return 0;
      }
    });

    // merge
    List<Appointment> merged = new ArrayList<Appointment>();
    merged.add(sorted.get(0));
    int i = 1;

    while (i < sorted.size()) {
      if (!merged.get(merged.size() - 1).getEndTime().before(sorted.get(i).getStartTime())) {
        merged.get(merged.size() - 1).setEndTime(sorted.get(i++).getEndTime());
      } else {
        merged.add(sorted.get(i++));
      }
    }

    return merged;
  }

  private List<WorkBreak> cleanupWorkBreaks(List<WorkBreak> workBreaks) {

    if (workBreaks == null || workBreaks.size() <= 1) {
      return workBreaks;
    }

    List<WorkBreak> sorted = new ArrayList<WorkBreak>(workBreaks);
    Collections.sort(sorted, new Comparator<WorkBreak>() {
      @Override
      public int compare(WorkBreak o1, WorkBreak o2) {

        if (o1.getStartBreak().before(o2.getStartBreak())) {
          return -1;
        } else if (o1.getStartBreak().after(o2.getStartBreak())) {
          return 1;
        }

        return 0;
      }
    });

    List<WorkBreak> merged = new ArrayList<WorkBreak>();
    merged.add(sorted.get(0));
    int i = 1;

    while (i < sorted.size()) {
      // !before instead of after, in order to include case where ==
      if (!merged.get(merged.size() - 1).getEndBreak().before(sorted.get(i).getStartBreak())) {

        java.sql.Time newEnd;
        newEnd = sorted.get(i).getEndBreak();
        if (merged.get(merged.size() - 1).getEndBreak().before(sorted.get(i).getEndBreak())) {
          newEnd = merged.get(merged.size() - 1).getEndBreak();
        }

        merged.get(merged.size() - 1).setEndBreak(newEnd);
        i++;
      } else {
        merged.add(sorted.get(i++));
      }
    }

    return merged;
  }

  private List<TechnicianHour> subtractWorkBreaksFromTechnicianHour(TechnicianHour technicianHour) {

    List<TechnicianHour> result = new ArrayList<TechnicianHour>();

    List<WorkBreak> workBreaks = cleanupWorkBreaks(new ArrayList<WorkBreak>(technicianHour.getWorkBreak()));

    java.sql.Time start = technicianHour.getStartTime();

    for (WorkBreak w : workBreaks) {

      if (!start.after(w.getStartBreak())) {
        TechnicianHour newHour = new TechnicianHour();
        newHour.setStartTime(start);
        newHour.setEndTime(w.getStartBreak());
        result.add(newHour);
      }

      start = w.getEndBreak();
    }

    if (start.before(technicianHour.getEndTime())) {
      TechnicianHour newHour = new TechnicianHour();
      newHour.setStartTime(start);
      newHour.setEndTime(technicianHour.getEndTime());
      result.add(newHour);
    }

    return result;
  }

  private List<TechnicianHour> subtractWorkBreaksFromTechnicianHours(List<TechnicianHour> technicianHours) {

    // needs merging?

    List<TechnicianHour> result = new ArrayList<TechnicianHour>();

    for (TechnicianHour h : technicianHours) {
      result.addAll(subtractWorkBreaksFromTechnicianHour(h));
    }

    return result;
  }

  // Organized by technician
  /**
   * A method which returns a map of technicians and their associated technicianHours for a given day
   * 
   * @param date; the date in question we are hoping to find technicians available on 
   * @return A map of technicians which are working on the specified day and their associated technicianHour schedule
   */
  private Map<Technician, List<TechnicianHour>> getTechnicianHoursByDate(java.sql.Date date) {

    Map<Technician, List<TechnicianHour>> result = new HashMap<Technician, List<TechnicianHour>>();

    List<TechnicianHour> technicianHoursByDate =
        new ArrayList<TechnicianHour>(technicianHourRepository.findTechnicianHourByDate(date));

    for (TechnicianHour h : technicianHoursByDate) {
      Technician t = technicianRepository.findTechnicianByTechnicianHour(h);
      if (result.containsKey(t)) {
        result.get(t).add(h);
      } else {

        List<TechnicianHour> newList = new ArrayList<TechnicianHour>();
        newList.add(h);

        result.put(t, newList);
      }
    }

    // return technicianHoursByDate.stream().collect(Collectors.groupingBy(TechnicianHour::getTechnician));

    return result;
  }

  // Organized by technician
  /**
   * A method which returns a map of technicians and their associated appointments for a given day
   * 
   * @param date; the date in question we are hoping to find technicians available on 
   * @return A map of technicians which are working on the specified day and their associated appointments they will be working on 
   */
  private Map<Technician, List<Appointment>> getTechnicianAppointmentsByDate(java.sql.Date date) {

    List<Appointment> technicianAppointmentsByDate =
        new ArrayList<Appointment>(appointmentRepository.findAppointmentByDate(date));

    return technicianAppointmentsByDate.stream().collect(Collectors.groupingBy(Appointment::getTechnician));
  }
  
  /**
   * A method which returns a list of updated technicianHours for a specific technician; creating chunks of unavailability based off of when they are working on appointments
   * 
   * @param technicianHoursForDay; a list of technicianHours on a specific day (not depicting when they are unavailable due to already being booked to work)
   * @param appointments; a list of appointments that the technician will be working on 
   * @return A list of technicianHours which has been updated/spots blocked off based on the unavailabilities that were provided as a list of appointments 
   */
  private List<TechnicianHour> subtractAppointmentsFromTechnicianHours(List<TechnicianHour> technicianHoursForDay,
      List<Appointment> appointments) {
    
    if (appointments == null) {
      return technicianHoursForDay;
    }

    List<TechnicianHour> technicianHours = new ArrayList<TechnicianHour>(technicianHoursForDay);

    // if "break inside appointment" assumption changes
    // use two loops, make sure to merge and create new slot if necessary:

    /*
     * while start appointments[j] > end technicianHours[i] i++
     * 
     * shorten technicianHours[i] by reducing end
     * 
     * while end of technicianHours[i] >= end of appointments[j] i++
     * 
     * shorten technicianHours[i] by increasing start
     * 
     * j++
     * 
     */

    int i = 0;
    int j = 0;

    while (j < appointments.size()) {

      // find appropriate technicianHour for appointment
      while (appointments.get(j).getStartTime().after(technicianHours.get(i).getEndTime())) {
        i++;
      }

      boolean strictStart = technicianHours.get(i).getStartTime().before(appointments.get(j).getStartTime());
      boolean strictEnd = appointments.get(j).getEndTime().before(technicianHours.get(i).getEndTime());

      // start < a < b < end
      if (strictStart && strictEnd) {

        TechnicianHour newTechnicianHour = new TechnicianHour();
        newTechnicianHour.setStartTime(appointments.get(j).getEndTime());
        newTechnicianHour.setEndTime(technicianHours.get(i).getEndTime());
        newTechnicianHour.setDate(appointments.get(j).getDate());

        technicianHours.get(i).setEndTime(appointments.get(j).getStartTime());
        technicianHours.add(i + 1, newTechnicianHour);

      }
      // start < a < b = end
      else if (strictStart) {
        technicianHours.get(i).setEndTime(appointments.get(j).getStartTime());
      }
      // start = a < b < end
      else if (strictEnd) {
        technicianHours.get(i).setStartTime(appointments.get(j).getEndTime());
      }
      // start = a < b = end
      else {
        technicianHours.remove(i);
      }

      j++;
    }

    return technicianHours;
  }
  
  /**
   * A method which returns a list of technicianHours that are longer than a specificed minimum duration
   * 
   * @param technicianHours; a list of technicianHours to be filtered through
   * @param minDurationInMin; the minimum specified duration that we want the technicianHour to be of (filtering criteria) 
   * @return A filtered list of technicianHours which are as long as or longer than the specified minimum duration
   */
  private List<TechnicianHour> filterByMinDuration(List<TechnicianHour> technicianHours, int minDurationInMin) {

    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();

    List<TechnicianHour> result = new ArrayList<TechnicianHour>();

    for (TechnicianHour h : technicianHours) {
      c1.setTime(h.getStartTime());
      c2.setTime(h.getEndTime());

      if (c2.getTimeInMillis() - c1.getTimeInMillis() >= 60000 * minDurationInMin) {
        result.add(h);
      }
    }

    return result;

  }

  /**
   * A method which logs in a user at start up
   * 
   * @param username; the username of the person trying to log in
   * @param password; the password of the person trying to log in
   * @return the endUser object that corresponds to the credentials that were provided
   */
  @Transactional
  public EndUser signIn(String endUserID, String password) throws IllegalArgumentException {
	EndUser endUser = endUserRepository.findEndUserByUsername(endUserID);
    if (endUser == null) {
      throw new IllegalArgumentException("Username cannot be found!");
    } else if (endUser.getPassword().equals(password) == false) {
      throw new IllegalArgumentException("Incorrect password!");
    }
    return endUser;
  }


  // Customer service methods
  
  /**
   * A method which returns all of the customers that exist
   * @return a list of customers that exist in the database
   */
  @Transactional
  public List<Customer> getAllCustomers() {
    return toList(customerRepository.findAll());
  }

  /**
   * A method which returns a customer if it can be found using a specific username
   * 
   * @param username; the account username of the customer we are seeking to find
   * @return A customer object whose account username is the username were provided as a parameter
   * @exception in the case where no customer exists based on the given username, we throw an exception 
   */
  @Transactional
  public Customer getCustomerByUsername(String username) throws IllegalArgumentException {
    Customer customer = customerRepository.findCustomerByUsername(username);

    if (customer == null) {
      throw new IllegalArgumentException("No customer exists with username " + username);
    }

    return customer;
  }

  /**
   * A method which creates a new customer
   * 
   * @param username; the username that will be tied to the account
   * @param password; the password that will be used to log in to the account
   * @param name; the legal name of the owner of the account that is being created
   * @param email; the email info of the owner of the account 
   * @return the newly created customer object
   * @exception in the case where the parameters provided are invalid, we throw an exception
   */
  @Transactional
  public Customer createCustomer(String username, String password, String name, String email)
      throws IllegalArgumentException {

    if (customerRepository.findCustomerByUsername(username) != null) {
      throw new IllegalArgumentException("Customer with username " + username + " already exists");
    }

    if (username == null) {
      throw new IllegalArgumentException("A username must be provided");
    }

    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("A password must be provided");
    }

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("A name must be provided");
    }

    if (email == null || email.isEmpty()) {
      throw new IllegalArgumentException("An email must be provided");
    }

    if (customerRepository.findCustomerByEmail(email) != null) {
      throw new IllegalArgumentException("Account with the provided email already exists");
    }

    Customer customer = new Customer();
    customer.setUsername(username);
    customer.setPassword(password);
    customer.setName(name);
    customer.setEmail(email);
    customer.setAmountOwed(0);
    customerRepository.save(customer);
    return customer;
  }



  // Administrator service methods

  /**
   * A method which returns all of the administrators that exist
   * @return a list of administrators that exist in the database
   */
  @Transactional
  public List<Administrator> getAllAdministrators() {
    return toList(administratorRepository.findAll());
  }
  
  /**
   * A method which returns a administrator if it can be found using a specific username
   * 
   * @param username; the account username of the administrator we are seeking to find
   * @return An administrator object whose account username is the username were provided as a parameter
   * @exception in the case where no administrator exists based on the given username, we throw an exception 
   */
  @Transactional
  public Administrator getAdministrator(String username) throws IllegalArgumentException {
    Administrator administrator = administratorRepository.findAdministratorByUsername(username);

    if (administrator == null) {
      throw new IllegalArgumentException("Administrator cannot be found");
    }

    return administrator;
  }

  /**
   * A method which takes the username of a customer and converts that customer account into an administrator
   * 
   * @param username; the account username of the customer we are seeking to convert
   * @return An newly created administrator object who was just created based off the info tied to the old customer account
   * @exception in the case where no customer exists based on the given username, we throw an exception 
   */
  @Transactional
  public Administrator makeAdministrator(String username) throws IllegalArgumentException {

    if (username == null) {
      throw new IllegalArgumentException("Cannot have null username");
    }

    Customer customer = customerRepository.findCustomerByUsername(username);

    if (customer == null) {
      throw new IllegalArgumentException("Specified customer does not exist");
    }

    customerRepository.delete(customer);
    Administrator administrator = new Administrator();
    administrator.setUsername(customer.getUsername());
    administrator.setPassword(customer.getPassword());
    administrator.setName(customer.getName());
    administrator.setEmail(customer.getEmail());
    administratorRepository.save(administrator);
    return administrator;
  }


  // endUser service methods
  /**
   * A method which returns all of the endUsers that exist
   * @return a list of endUsers that exist in the database
   */
  @Transactional
  public List<EndUser> getAllEndUsers() {
    return toList(endUserRepository.findAll());
  }
  
  /**
   * A method which returns a endUser if it can be found using a specific username
   * 
   * @param username; the account username of the endUser we are seeking to find
   * @return An endUser object whose account username is the username were provided as a parameter
   * @exception in the case where no endUser exists based on the given username, we throw an exception 
   */
  @Transactional
  public EndUser getEndUser(String username) throws IllegalArgumentException {
    EndUser endUser = endUserRepository.findEndUserByUsername(username);
    if (endUser == null) {
      throw new IllegalArgumentException("Username " + username + " cannot be found!");
    }
    return endUser;
  }

  /**
   * A method which updates the info of an endUser(or one of its subclasses) if it can be found using the primary key 
   * 
   * @param userId; the primary key of the endUser which we are seeking to find
   * @param password; the password we would like to update to 
   * @param name; the name we would like to update to
   * @param email; the email we would like to update to
   * @return The endUser which we found and whose info we updated
   * @exception in the case where no endUser exists based on the given username or invalid parameters were passed, we throw an exception 
   */
  @Transactional
  public EndUser updateEndUser(String userId, String password, String name, String email)
      throws IllegalArgumentException {

    if (nonValidString(userId)) {
      throw new IllegalArgumentException("Must enter a proper username!");
    }

    EndUser endUser = endUserRepository.findEndUserByUsername(userId);

    if (endUser == null) {
      throw new IllegalArgumentException("Username cannot be found!");
    }

    if (nonValidString(email)) {
      throw new IllegalArgumentException("Must enter a valid email!");
    }

    if ((!email.equals(endUser.getEmail()) && (endUserRepository.findEndUserByEmail(email) != null))) {
      throw new IllegalArgumentException("Email already exists!");
    }

    if (nonValidString(password)) {
      throw new IllegalArgumentException("Must enter a valid password!");
    }

    if (nonValidString(name)) {
      throw new IllegalArgumentException("Must enter a valid name!");
    }

    endUser.setPassword(password);
    endUser.setName(name);
    endUser.setEmail(email);

    endUserRepository.save(endUser);
    return endUser;
  }
  
  /**
   * A method which deletes an endUser (including if it is a subclass)
   * 
   * @param username; the account username of the endUser we are seeking to find
   * @return non existing endUser which was just recently deleted
   * @exception in the case where no endUser exists based on the given username, we throw an exception 
   */
  @Transactional
  public EndUser deleteEndUser(String username) throws IllegalArgumentException {

    if (nonValidString(username)) {
      throw new IllegalArgumentException("Must enter a proper username!");
    }

    EndUser endUser = endUserRepository.findEndUserByUsername(username);

    if (endUser == null) {
      throw new IllegalArgumentException("Specified user does not exist");
    }

    // Delete all the user's associated appointments
    Set<Appointment> appointments = new HashSet<Appointment>();
    if (endUser instanceof Customer) {
      appointments = appointmentRepository.findAppointmentByCustomer((Customer) endUser);
    } else if (endUser instanceof Technician) {
      appointments = appointmentRepository.findAppointmentByTechnician((Technician) endUser);
    }
    for (Appointment appointment : appointments) {
      appointmentRepository.delete(appointment);
    }

    endUserRepository.delete(endUser);
    return endUser;
  }


  // Technician service methods
  /**
   * A method which returns all of the technicians that exist
   * @return a list of technicians that exist in the database
   */
  @Transactional
  public List<Technician> getAllTechnicians() {
    return toList(technicianRepository.findAll());
  }

  /**
   * A method which returns a technician if it can be found using a specific username
   * 
   * @param username; the account username of the technician we are seeking to find
   * @return A technician object whose account username is the username were provided as a parameter
   * @exception in the case where no technician exists based on the given username, we throw an exception 
   */
  @Transactional
  public Technician getTechnicianByUsername(String username) throws IllegalArgumentException {

    if (nonValidString(username)) {
      throw new IllegalArgumentException("A valid username must be provided");
    }

    Technician technician = technicianRepository.findTechnicianByUsername(username);

    if (technician == null) {
      throw new IllegalArgumentException("Technician cannot be found.");
    }

    return technician;
  }

  /**
   * A method which takes the username of a customer and converts that customer account into a technician
   * 
   * @param username; the account username of the customer we are seeking to convert
   * @return An newly created technician object who was just created based off the info tied to the old customer account
   * @exception in the case where no customer exists based on the given username, we throw an exception 
   */
  @Transactional
  public Technician makeTechnician(String username) throws IllegalArgumentException {

    if (nonValidString(username)) {
      throw new IllegalArgumentException("Must enter a valid username!");
    }

    Customer customer = customerRepository.findCustomerByUsername(username);

    if (customer == null) {
      throw new IllegalArgumentException("Specified customer does not exist");
    }

    customerRepository.delete(customer);
    Technician technician = new Technician();
    technician.setUsername(customer.getUsername());
    technician.setPassword(customer.getPassword());
    technician.setName(customer.getName());
    technician.setEmail(customer.getEmail());
    technician.setTechnicianHour(new HashSet<TechnicianHour>());
    technicianRepository.save(technician);
    return technician;
  }


  // Appointment service methods
  /**
   * A method which provides the appointments attended by a customer
   * 
   * @param customer; the customer in question whose appointments we desire 
   * @return A set of appointments that was attended by the customer parameter 
   */
  @Transactional
  public Set<Appointment> getAppointmentsAttendedByCustomer(Customer customer) {
    Set<Appointment> appointmentsAttendedByCustomer = new HashSet<Appointment>();
    for (Appointment a : appointmentRepository.findAppointmentByCustomer(customer)) {
      appointmentsAttendedByCustomer.add(a);
    }
    return appointmentsAttendedByCustomer;
  }

  // workItem service methods
  
  /**
   * A method which creates new workItems which can be booked by a customer
   * 
   * @param name; the name of the new workItem 
   * @param duration; the duration of time that the workItem will require to be completed
   * @param price; the cost of booking and performing the new workItem
   * @return the newly created workItem object
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public WorkItem createWorkItem(String name, int duration, int price) throws IllegalArgumentException {
    if (name == null || name == "") {
      throw new IllegalArgumentException("A valid service name must be provide!");
    }

    if (workItemRepository.findWorkItemByName(name) != null) {
      throw new IllegalArgumentException("WorkItem with name: " + name + " already exists");
    }

    if (duration < 0) {
      throw new IllegalArgumentException("Duration cannot be less than zero!");
    }

    if (price < 0) {
      throw new IllegalArgumentException("Price cannot be less than zero!");
    }

    WorkItem workItem = new WorkItem();
    workItem.setName(name);
    workItem.setDuration(duration);
    workItem.setPrice(price);

    workItemRepository.save(workItem);
    return workItem;
  }

  /**
   * A method which finds a workItem based off a specific name
   * 
   * @param name; the name of the workItem we are seeking
   * @return the workItem object which we were looking for
   * @exception if no workItem object with the provided name can be found, we throw an exception
   */
  @Transactional
  public WorkItem getWorkItem(String name) {
    WorkItem workItem = workItemRepository.findWorkItemByName(name);

    if (workItem == null) {
      throw new IllegalArgumentException("Work item cannot be found!");
    }
    return workItem;
  }

  /**
   * A method which updates an existing workItem object
   * 
   * @param name; the name of the workItem we wish to update/find
   * @param duration; the new duration of time that the workItem will be updated to
   * @param price; the new cost of booking that the workItem will be updated to
   * @return the newly updated pre-existing workItem object
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public WorkItem updateWorkItem(String name, int duration, int price) {
    if (name == null || name == "") {
      throw new IllegalArgumentException("A valid service name must be provide!");
    }

    WorkItem workItem = workItemRepository.findWorkItemByName(name);

    if (workItem == null) {
      throw new IllegalArgumentException("WorkItem with name does not exist!");
    }

    if (duration < 0) {
      throw new IllegalArgumentException("Duration cannot be less than zero!");
    }

    if (price < 0) {
      throw new IllegalArgumentException("Price cannot be less than zero!");
    }

    workItem.setName(name);
    workItem.setDuration(duration);
    workItem.setPrice(price);

    workItemRepository.save(workItem);
    return workItem;
  }

  /**
   * A method which deletes an existing workItem object
   * 
   * @param name; the name of the existing workItem we wish to delete
   * @return the non-existing workItem which we just deleted
   * @exception in the case where invalid inputs are provided/the workItem cannot be found, an exception is thrown
   */
  @Transactional
  public WorkItem deleteWorkItem(String name) {
    if (name == null || name == "") {
      throw new IllegalArgumentException("A valid name must be provided");
    }

    WorkItem workItem = workItemRepository.findWorkItemByName(name);

    if (workItem == null) {
      throw new IllegalArgumentException("Work item cannot be found.");
    }

    Set<Appointment> appointments = appointmentRepository.findAppointmentByWorkItem(workItem);

    if (appointments.isEmpty() == false) {
      throw new IllegalArgumentException("There are appointments associated with this work item; it cannot be deleted!");
    }

    workItemRepository.delete(workItem);
    return workItem;
  }
  
  /**
   * A method which returns all workItems which can be booked by a customer
   * 
   * @return a list of all possible/existing workItems
   */
  @Transactional
  public List<WorkItem> getAllWorkItems() {
    return toList(workItemRepository.findAll());
  }

  // WorkBreak service methods
  
  
  /**
   * A method which creates new workBreaks which can be used by technicians
   * 
   * @param workHourId; the primary key of the workHour that the workBreak is associated to
   * @param starTime; the startTime of the workBreak
   * @param endTime; the endTime of the workBreak
   * @return the newly created workBreak object
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public WorkBreak createWorkBreak(Integer workHourId, Time startTime, Time endTime) throws IllegalArgumentException {

    if (workHourId == null) {
      throw new IllegalArgumentException("A valid work hour ID must be provided!");
    }
    
    if (startTime == null) {
    	 throw new IllegalArgumentException("A valid start time must be provided!");
    }
    
    if (endTime == null) {
   	 throw new IllegalArgumentException("A valid end time must be provided!");
    }
    
    if (!(startTime.before(endTime))) {
      throw new IllegalArgumentException("Start time cannot be after end time");
    }
    WorkHour workHour = workHourRepository.findWorkHourById(workHourId);

    if (workHour == null) {
      throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
    }

    // Make sure work break time is within workHour time
    if ((startTime.before(workHour.getStartTime())) || (endTime.after(workHour.getEndTime()))) {
      throw new IllegalArgumentException("Work break must be within work hour");
    }

    Set<WorkBreak> workBreaks = workHour.getWorkBreak();

    // Make sure work break does not overlap with existing work break associated with work hour
    for (WorkBreak w : workBreaks) {

      if (!(startTime.before(w.getEndBreak()))) {
        continue;
      } else if (!(w.getStartBreak().before(endTime))) {
        continue;
      } else {
        throw new IllegalArgumentException("Work break overlaps with existing work hour");
      }
    }


    WorkBreak workBreak = new WorkBreak();
    workBreak.setStartBreak(startTime);
    workBreak.setEndBreak(endTime);


    workBreaks.add(workBreak);

    workHour.setWorkBreak(workBreaks);
    workBreakRepository.save(workBreak);
    workHourRepository.save(workHour);

    return workBreak;
  }

  /**
   * A method which finds a specific workBreak based off its primary key
   * 
   * @param workBreakId; the primary key of the existing workBreak 
   * @return the existing workBreak item that is tied to the given parameter
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public WorkBreak getWorkBreak(Integer workBreakId) throws IllegalArgumentException {

    if (workBreakId == null) {
      throw new IllegalArgumentException("A valid work break ID must be provided!");
    }

    WorkBreak workBreak = workBreakRepository.findWorkBreakById(workBreakId);

    if (workBreak == null)
      throw new IllegalArgumentException("The provided work Break Id is not associated with a work break");
    return workBreak;

  }

  /**
   * A method which updates a specific workBreak found via its primary key
   * 
   * @param workBreakId; the primary key of the existing workBreak 
   * @param newStartTime; the new start time of the existing workBreak
   * @param newEndTime; the new end time of the existing workBreak
   * @return the newly updated workBreak item that was previously existing/found
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public WorkBreak updateWorkBreak(Integer workBreakId, Time newStartTime, Time newEndTime) {

    if (workBreakId == null) {
      throw new IllegalArgumentException("A valid work break ID must be provided!");
    }

    WorkBreak workBreak = workBreakRepository.findWorkBreakById(workBreakId);

    if (workBreak == null) {
      throw new IllegalArgumentException("Specified work break doesn't exist!");
    }

    WorkHour workHour = workHourRepository.findWorkHourByWorkBreak(workBreak);
    Set<WorkBreak> workBreakSet = workHour.getWorkBreak();

    // Make sure new work break time is within work hour:
    if ((newStartTime.before(workHour.getStartTime())) || (newEndTime.after(workHour.getEndTime()))) {
      throw new IllegalArgumentException("Work break must be within work hour");
    }

    // Make sure work break does not overlap with existing work break (not including the work break being updated)
    for (WorkBreak w : workBreakSet) {
      if (w.getId().equals(workBreak.getId())) {
        continue;
      } else if (!(newStartTime.before(w.getEndBreak()))) {
        continue;
      } else if (!(w.getStartBreak().before(newEndTime))) {
        continue;
      } else {
        throw new IllegalArgumentException("Updated work break overlaps with existing work break");
      }
    }

    workBreakSet.remove(workBreak);
    workBreak.setStartBreak(newStartTime);
    workBreak.setEndBreak(newEndTime);

    workBreakSet.add(workBreak);

    workHour.setWorkBreak(workBreakSet);
    workHourRepository.save(workHour);
    workBreakRepository.save(workBreak);
    return workBreak;
  }

  /**
   * A method which deletes a specific workBreak found via its primary key
   * 
   * @param workBreakId; the primary key of the existing workBreak 
   * @return the non-existing workBreak item that was recently deleted
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public WorkBreak deleteWorkBreak(Integer workBreakId) throws IllegalArgumentException {

    if (workBreakId == null) {
      throw new IllegalArgumentException("A valid work break ID must be provided!");
    }

    WorkBreak workBreak = workBreakRepository.findWorkBreakById(workBreakId);

    if (workBreak == null) {
      throw new IllegalArgumentException("Specified Work Break doesn't exist!");
    }

    workBreakRepository.delete(workBreak);
    return workBreak;
  }

  // TechnicianHour service methods
  
  /**
   * A method which creates a new TechnicianHour object that needs to be associated with a Technician 
   * 
   * @param technicianUsername; the primary key of the existing Technician that will be associated to the new TechnicianHour 
   * @param starTime; the time when the TechnicianHour will start 
   * @param endTime; the time when the TechnicianHour will end
   * @param date; the date when the TechnicianHour will occur 
   * @return the newly created TechnicianHour object
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
  @Transactional
  public TechnicianHour createTechnicianHour(String technicianUsername, Time startTime, Time endTime, Date date) {

    if (technicianUsername == null || (technicianUsername.trim().isEmpty())) {
      throw new IllegalArgumentException("A valid technician username must be provided!");
    }

    Technician technician = technicianRepository.findTechnicianByUsername(technicianUsername);

    if (technician == null) {
      throw new IllegalArgumentException("Specified technician doesn't exist!");
    }

    if (endTime == null) {
      throw new IllegalArgumentException("A valid end time must be provided!");
    }

    if (startTime == null) {
      throw new IllegalArgumentException("A valid start time must be provided!");
    }

    if (endTime.before(startTime) == true) {
      throw new IllegalArgumentException("End time cannot be before start time");
    }

    if (date == null) {
      throw new IllegalArgumentException("A valid date must be provided!");
    }

    // check that technician hour is within business hour of the same day
    Set<BusinessHour> businessHourSet = businessHourRepository.findBusinessHourByDate(date);
	if(businessHourSet.size() == 0) {
		throw new IllegalArgumentException("Technician hour must exist within a business hour");
	}
	
    for (BusinessHour b : businessHourSet) {
      if (startTime.before(b.getStartTime()) || (endTime.after(b.getEndTime()))) {
        throw new IllegalArgumentException("Technician hour must exist within a business hour");
      }
    }

    // check that technician hour does not overlap with existing technician hour of same technician
    Set<TechnicianHour> technicianHourSet = technician.getTechnicianHour();

    for (TechnicianHour t : technicianHourSet) {
      if (t.getDate().equals(date)) {
        if (!(startTime.before(t.getEndTime()))) {
          continue;
        } else if (!(t.getStartTime().before(endTime))) {
          continue;
        } else {
          throw new IllegalArgumentException(
              "Technician hour cannot overlap with another technicianHour of the same technician");
        }
      }
    }

    TechnicianHour technicianHour = new TechnicianHour();
    technicianHour.setStartTime(startTime);
    technicianHour.setEndTime(endTime);
    technicianHour.setDate(date);

    Set<WorkBreak> emptyWorkBreak = new HashSet<WorkBreak>();
    technicianHour.setWorkBreak(emptyWorkBreak);

    technicianHourSet.add(technicianHour);
    technician.setTechnicianHour(technicianHourSet);
    technicianHourRepository.save(technicianHour);
    technicianRepository.save(technician);



    return technicianHour;
  }
  
  /**
   * A method which finds a TechnicianHour object via its primary key
   * 
   * @param id; the primary key of the existing TechnicianHour that we are seeking
   * @return the existing TechnicianHour object that we were seeking
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
	@Transactional
	public TechnicianHour getTechnicianHour(Integer id) throws IllegalArgumentException {
		TechnicianHour technicianHour = technicianHourRepository.findTechnicianHourById(id);
	
		if (technicianHour == null) {
			throw new IllegalArgumentException("Technician Hour cannot be found!");
		}
		return technicianHour;
	}
  
	
	/**
	   * A method which finds all TechnicianHour objects falling on a specific date
	   * 
	   * @param date; the Date object in question which we are seeking all TechnicianHours of 
	   * @return a list of TechnicianHours which fall on the specified date parameter
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public List<TechnicianHour> getAllTechnicianHoursByDate(Date date) throws IllegalArgumentException {
		
		if (date == null) {
			throw new IllegalArgumentException("A valid date must be provided!");
		}
		
		Set<TechnicianHour> technicianHours = technicianHourRepository.findTechnicianHourByDate(date);
		
		return toList(technicianHours);
	}

	/**
	   * A method which finds and updates an existing TechnicianHour via its primary key
	   * 
	   * @param technicianHourId; the primary key of the TechnicianHour which we seek to update
	   * @param startTime; the new start time that the TechnicianHour will update to
	   * @param endTime; the new end time that the TechnicianHour will update to
	   * @param date; the new date that the TechnicianHour will update to
	   * @return the newly updated TechnicianHour that was previously existing 
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
  @Transactional
  public TechnicianHour updateTechnicianHour(Integer technicianHourId, Time startTime, Time endTime, Date date)
      throws IllegalArgumentException {

    if (technicianHourId == null) {
      throw new IllegalArgumentException("A valid technican hour I.D. must be provided!");
    }

    TechnicianHour technicianHour = technicianHourRepository.findTechnicianHourById(technicianHourId);

    if (technicianHour == null) {
      throw new IllegalArgumentException("A technician hour with this I.D. cannot be found!");
    }

    Technician technician = technicianRepository.findTechnicianByTechnicianHour(technicianHour);

    if (technician == null) {
      throw new IllegalArgumentException(
          "Specified technician hour doesn't exist for any technician!");
    }

    if (date == null) {
      throw new IllegalArgumentException("A valid date must be provided!");
    }

    if (startTime == null) {
      throw new IllegalArgumentException("A valid start time must be provided!");
    }
    
    if (endTime == null) {
        throw new IllegalArgumentException("A valid end time must be provided!");
    }
    
    if (endTime.before(startTime) == true) {
        throw new IllegalArgumentException("End time cannot be before start time!");
    }

    // check that technician hour is within business hour of the same day
    Set<BusinessHour> businessHourSet = businessHourRepository.findBusinessHourByDate(date);
    for (BusinessHour b : businessHourSet) {
      if (startTime.before(b.getStartTime()) || (endTime.after(b.getEndTime()))) {
        throw new IllegalArgumentException("Technician hour must exist within a business hour");
      }
    }

    // check that technician hour does not overlap with existing technician hour of same technician
    Set<TechnicianHour> technicianHourSet = technician.getTechnicianHour();

    for (TechnicianHour t : technicianHourSet) {
      if (t.getDate().equals(technicianHour.getDate())) {
        if (t.getId().equals(technicianHourId)) {
          continue;
        } else if (!(startTime.before(t.getEndTime()))) {
          continue;
        } else if (!(t.getStartTime().before(endTime))) {
          continue;
        } else {
          throw new IllegalArgumentException(
              "Technician hour cannot overlap with another technicianHour of the same technician");
        }
      }
    }

    technicianHour.setStartTime(startTime);
    technicianHour.setEndTime(endTime);
    technicianHour.setDate(date);

    technicianRepository.save(technician);
    technicianHourRepository.save(technicianHour);

    return technicianHour;
  }


  // could be changed to update singular workBreak
  /**
   * A method which finds and deletes an existing TechnicianHour via its primary key
   * 
   * @param id; the primary key of the TechnicianHour which we seek to delete
   * @return the non-existing  TechnicianHour that we just deleted
   * @exception in the case where invalid inputs are provided, an exception is thrown
   */
	@Transactional
	public TechnicianHour deleteTechnicianHour(Integer id) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid technician hour ID must be provided!");
		}
		
		TechnicianHour technicianHour = technicianHourRepository.findTechnicianHourById(id);
		
		if (technicianHour == null) {
			throw new IllegalArgumentException("Specified technician Hour doesn't exist!");
		}
		
		// Delete all appointments associated with this technician hour
		Technician technician = technicianRepository.findTechnicianByTechnicianHour(technicianHour);
		
		Set<Appointment> appointmentSet = appointmentRepository.findAppointmentByTechnician(technician);
		
		for(Appointment a : appointmentSet) {
			if(a.getDate().equals(technicianHour.getDate())) {
				if((!(a.getStartTime().before(technicianHour.getStartTime()))) && (!(a.getEndTime().after(technicianHour.getEndTime())))) {
					appointmentRepository.delete(a);
				}
			}
		}
		technicianHourRepository.delete(technicianHour);
		return technicianHour;
	}
	
	//BusinessHour service methods
	
	/**
	   * A method which creates a new BusinessHour object
	   * 
	   * @param startTime; the time that the BusinessHour will start at
	   * @param endTime; the time that the BusinessHour will end at
	   * @param date; the date that we wish the BusinessHour to be linked to/occur on
	   * @return the newly created BusinessHour that we just made 
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public BusinessHour createBusinessHour(Time startTime, Time endTime, Date date) {
		
		if (startTime == null) {
			throw new IllegalArgumentException("A valid start time must be provided!");
		}
		
		if (endTime == null) {
			throw new IllegalArgumentException("A valid end time must be provided!");
		}
		
		if (!startTime.before(endTime)) {
			throw new IllegalArgumentException("Start time must be before end time");
		}
		
		if (date == null) {
			throw new IllegalArgumentException("A valid date must be provided!");
		}
		
		//Check that businessHour does not overlap with another businessHour
		Set<BusinessHour> businessHourSet = businessHourRepository.findBusinessHourByDate(date);
		
		for(BusinessHour b : businessHourSet) {
			if(!(startTime.before(b.getEndTime()))) {
				continue;
			} else if(!(b.getStartTime().before(endTime))) {
				continue;
			} else {
				throw new IllegalArgumentException("Business hour cannot overlap with an existing business hour");
			}
		}
		
		BusinessHour businessHour = new BusinessHour();
		businessHour.setStartTime(startTime);
		businessHour.setEndTime(endTime);
		businessHour.setDate(date);
		
		Set<WorkBreak> emptyWorkBreak = new HashSet<WorkBreak>();
		businessHour.setWorkBreak(emptyWorkBreak);
		
		businessHourRepository.save(businessHour);
		
		return businessHour;
	}
	
	/**
	   * A method which finds a BusinessHour object via the primary key
	   * 
	   * @param id; the primary key of the businessHour that we wish to find
	   * @return the BusinessHour that we were seeking to find
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public BusinessHour getBusinessHour(Integer id) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid business hour ID must be provided!");
		}
		
		BusinessHour businessHour = businessHourRepository.findBusinessHourById(id);
		
		if (businessHour == null) {
			throw new IllegalArgumentException("Business hour cannot be found!");
		}
		return businessHour;
	}
	
	/**
	   * A method which finds all BusinessHours occurring on a specific date
	   * 
	   * @param date; the date that we wish to find BusinessHours for
	   * @return a list of all BusinessHours which are tied to a specific date 
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public List<BusinessHour> getBusinessHoursByDate(Date date) throws IllegalArgumentException {
		
		if (date == null) {
			throw new IllegalArgumentException("A valid date must be provided!");
		}
		
		Set<BusinessHour> businessHours = businessHourRepository.findBusinessHourByDate(date);

		return toList(businessHours);
	}
	
	
	/**
	   * A method which finds and updates an existing BusinessHour via its primary key
	   * 
	   * @param id; the primary key of the BusinessHour which we seek to update
	   * @param startTime; the new start time that the BusinessHour will update to
	   * @param endTime; the new end time that the BusinessHour will update to
	   * @param date; the new date that the BusinessHour will occur on
	   * @return the newly updated BusinessHour that was previously existing 
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public BusinessHour updateBusinessHour(Integer id,Time startTime, Time endTime, Date date) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid business hour ID must be provided!");
		}
		
		BusinessHour businessHour = businessHourRepository.findBusinessHourById(id);
		
		if(businessHour == null) {
			throw new IllegalArgumentException("A business hour with this ID cannot be found!");
		}
		
		if (date == null) {
			throw new IllegalArgumentException("A valid date must be provided!");
		}
		
		if (startTime == null) {
			throw new IllegalArgumentException("A valid start time must be provided!");
		}
		
		if (endTime == null) {
			throw new IllegalArgumentException("A valid end time must be provided!");
		}
		
		if (!startTime.before(endTime)) {
			throw new IllegalArgumentException("Start time must be before end time");
		}
		
		Set<BusinessHour> businessHourSet = businessHourRepository.findBusinessHourByDate(date);
		
		for(BusinessHour b : businessHourSet) {
			if(b.getId().equals(id)) {
				continue;
			} else if(!(startTime.before(b.getEndTime()))) {
				continue;
			} else if(!(b.getStartTime().before(endTime))) {
				continue;
			} else {
				throw new IllegalArgumentException("Business hour cannot overlap with an existing business hour");
			}
		}
		
		Set<TechnicianHour> technicianHourSet = technicianHourRepository.findTechnicianHourByDate(date);
		for(TechnicianHour t : technicianHourSet) {
			if(t.getStartTime().before(startTime) || t.getEndTime().after(endTime)) {
				throw new IllegalArgumentException("Business hour cannot be changed such that existing technician hours are invalid");
			}
		}
		
		
		
		businessHour.setStartTime(startTime);
		businessHour.setEndTime(endTime);
		businessHour.setDate(date);

		businessHourRepository.save(businessHour);
		
		return businessHour;
	}
	
	/**
	   * A method which finds and deletes an existing BusinessHour via its primary key
	   * 
	   * @param id; the primary key of the BusinessHour which we seek to delete
	   * @return the non-existing  BusinessHour that we just deleted
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public BusinessHour deleteBusinessHour(Integer id) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid business hour ID must be provided!");
		}
		
		BusinessHour businessHour = businessHourRepository.findBusinessHourById(id);
		
		if (businessHour == null) {
			throw new IllegalArgumentException("Specified business hour doesn't exist!");
		}
		
		// Delete all appointments and technician hours which exist within the business hour
		Set<TechnicianHour> technicianHourSet = technicianHourRepository.findTechnicianHourByDate(businessHour.getDate());
		
		for(TechnicianHour t : technicianHourSet) {
			if((!(t.getStartTime().before(businessHour.getStartTime()))) && (!(t.getEndTime().after(businessHour.getEndTime())))) {
				technicianHourRepository.delete(t);
			}
		}
		
		Set<Appointment> appointmentSet = appointmentRepository.findAppointmentByDate(businessHour.getDate());
		
		for(Appointment a : appointmentSet) {
			if((!(a.getStartTime().before(businessHour.getStartTime()))) && (!(a.getEndTime().after(businessHour.getEndTime())))) {
				appointmentRepository.delete(a);
			}
		}
		businessHourRepository.delete(businessHour);
		return businessHour;
	}
	
	/**
	   * A method which finds all existing BusinessHours
	   * 
	   * @return a list of all of the existing BusinessHours in the database
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public List<BusinessHour> getAllBusinessHours(){
		return toList(businessHourRepository.findAll());
	}
	
	
	//Payment service methods
	
	
	/**
	   * A method which finds a customer via the username and updates the amount they are owing to the company
	   * 
	   * @param username; the primary key of the customer which we seek to find
	   * @param amountPayed; the total amount of money they are paying off from their total owed
	   * @return the customer which was found via the username and just made a payment
	   * @exception in the case where invalid inputs are provided, an exception is thrown
	   */
	@Transactional
	public Customer payment(String username, Integer amountPayed) throws IllegalArgumentException {
		if (nonValidString(username)) {
			throw new IllegalArgumentException("A valid username must be provided!");
		}
		
		Customer customer = customerRepository.findCustomerByUsername(username);
		
		if (customer == null) {
			throw new IllegalArgumentException("Specified customer does not exist!");
		}
		
		//Makes sure a customer's amount due doesn't go in the negatives
		Integer amountLeft = 0;
		if (customer.getAmountOwed() > amountPayed) amountLeft = customer.getAmountOwed() - amountPayed;
		
		customer.setAmountOwed(amountLeft);
		
		return customer;
	}
	
	//Helper methods
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	private boolean nonValidString(String string) {
		return (string==null)||(string.trim().isEmpty());
	}
}
