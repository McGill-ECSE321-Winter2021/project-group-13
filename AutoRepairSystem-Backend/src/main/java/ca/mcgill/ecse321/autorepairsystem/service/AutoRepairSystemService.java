package ca.mcgill.ecse321.autorepairsystem.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.autorepairsystem.dao.AppointmentRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.TechnicianHourRepository;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Service;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import org.springframework.beans.factory.annotation.Autowired;

public class AutoRepairSystemService {
  
  @Autowired
  TechnicianHourRepository technicianHourRepository;
  
  @Autowired
  AppointmentRepository appointmentRepository;
  
  /*
   * 
   * Getters and Setters
   * 
   */
  
  //always set Date when creating slots
  //did I forget to copy array anywhere?
  
  @Transactional
  public List<Appointment> getAllAppointments() {
 
      return toList(appointmentRepository.findAll());
  }
  
  @Transactional
  public List<Appointment> getAppointmentsByCustomer(Customer customer) {
    
    //need to check if customer in database?
    
    List<Appointment> appointments = appointmentRepository.findByCustomer(customer);
    
    return appointments;
  }
  
  @Transactional
  public List<Appointment> getAppointmentsByDate(java.sql.Date date) {
    
    List<Appointment> appointments = appointmentRepository.findByDate(date);
    
    return appointments;
  }
  
  @Transactional
  public List<Appointment> getAppointmentsByTechnician(Technician technician) {
    
    //need to check if technician in database?
    //ex: getCustomerByAppointment doesn't check appointment exists
    
    List<Appointment> appointments = appointmentRepository.findByTechnician(technician);
    
    return appointments;
  }
  
  //add more getters and setters
  //need to add for individual appointment finder? for each of the repository queries?
  //Services, Time, Technician...
  
  /*
   * View Available Appointments
   * 
   * Assumptions:
   * -breaks are within technical hours time
   * -appointments are within technical hours time
   * -Service.getDuration() in minutes
   * 
   * Not assumed:
   * -no overlap between breaks
   * 
   */
  
  public List<Appointment> cleanupAppointments(List<Appointment> appointments){
    
    //sort
    List<Appointment> sorted = new ArrayList<Appointment>(appointments);
    Collections.sort(sorted, new Comparator<Appointment>() {
      @Override
      public int compare(Appointment o1, Appointment o2) {
        
          if (o1.getStartTime().before(o2.getStartTime())) {
            return -1;
          }
          else if (o1.getStartTime().after(o2.getStartTime())) {
            return 1;
          }
          
          return 0;
      }
    });
    
    //merge
    List<Appointment> merged = new ArrayList<Appointment>();
    int i = 0;
    
    while(i < sorted.size()) {
      if (!merged.get(merged.size()-1).getEndTime().after(sorted.get(i).getStartTime())) {
        merged.get(merged.size()-1).setEndTime(sorted.get(i++).getEndTime());
      }
      else {
        merged.add(sorted.get(i++));
      }
    }
    
    return merged;
  }
  
  public List<WorkBreak> cleanupWorkBreaks(List<WorkBreak> workBreaks){
    
    //sort
    List<WorkBreak> sorted = new ArrayList<WorkBreak>(workBreaks);
    Collections.sort(sorted, new Comparator<WorkBreak>() {
      @Override
      public int compare(WorkBreak o1, WorkBreak o2) {
        
          if (o1.getStartBreak().before(o2.getStartBreak())) {
            return -1;
          }
          else if (o1.getStartBreak().after(o2.getStartBreak())) {
            return 1;
          }
          
          return 0;
      }
    });
    
    //merge
    List<WorkBreak> merged = new ArrayList<WorkBreak>();
    int i = 0;
    
    while(i < sorted.size()) {
      //!after in order to include case where ==
      if (!merged.get(merged.size()-1).getEndBreak().after(sorted.get(i).getStartBreak())) {
        merged.get(merged.size()-1).setEndBreak(sorted.get(i++).getEndBreak());
      }
      else {
        merged.add(sorted.get(i++));
      }
    }
    
    return merged;
  }
  
  public List<TechnicianHour> subtractWorkBreaksFromTechnicianHour(TechnicianHour technicianHour){
    
    List<TechnicianHour> result = new ArrayList<TechnicianHour>();
    
    Set<WorkBreak> workBreaks = technicianHour.getWorkBreak();
    List<WorkBreak> workBreaksToList = cleanupWorkBreaks(new ArrayList<WorkBreak>(workBreaks));
    
    java.sql.Time start = technicianHour.getStartTime();
    
    //add condition for when w.getStartBreak() == start
    for (WorkBreak w : workBreaksToList) {
      TechnicianHour newHour = new TechnicianHour();
      newHour.setStartTime(start);
      newHour.setEndTime(w.getStartBreak());
      result.add(newHour);
      start = w.getStartBreak();
    }
    
    if (start.before(technicianHour.getEndTime())) {
      TechnicianHour newHour = new TechnicianHour();
      newHour.setStartTime(start);
      newHour.setEndTime(technicianHour.getEndTime());
      result.add(newHour);
    }
    
    return result;
  }
  
  public List<TechnicianHour> subtractWorkBreaksFromTechnicianHours(List<TechnicianHour> technicianHours){
    List<TechnicianHour> result = new ArrayList<TechnicianHour>();
    
    for (TechnicianHour h : technicianHours) {
      result.addAll(subtractWorkBreaksFromTechnicianHour(h));
    }
    
    return result;
  }
  
  @Transactional
  //Organized by technician
  public Map<Technician, List<TechnicianHour>> getTechnicianHoursByDate(java.sql.Date date) {
    
    List<TechnicianHour> technicianHoursByDate = technicianHourRepository.findByDate(date);
    
    return technicianHoursByDate.stream().collect(Collectors.groupingBy(TechnicianHour::getTechnician));
  }
  
  @Transactional
  //Organized by technician
  public Map<Technician, List<Appointment>> getTechnicianAppointmentsByDate(java.sql.Date date) {
    
    List<Appointment> technicianAppointmentsByDate = appointmentRepository.findByDate(date);
    
    return technicianAppointmentsByDate.stream().collect(Collectors.groupingBy(Appointment::getTechnician));
  }
  
  public List<TechnicianHour> subtractAppointmentsFromTechnicianHours(List<TechnicianHour> technicianHoursForDay, List<Appointment> appointments){
    
    List<TechnicianHour> technicianHours = new ArrayList<TechnicianHour>(technicianHoursForDay);
    
    //if "break inside appointment" assumption changes
    //use two loops, make sure to merge and create new slot if necessary:
    
    /*
     * while start appointments[j] > end technicianHours[i]
     *  i++
     *  
     * shorten technicianHours[i] by reducing end
     * 
     * while end of technicianHours[i] >= end of appointments[j]
     *  i++
     * 
     * shorten technicianHours[i] by increasing start
     * 
     * j++
     * 
     */
    
    int i = 0;
    int j = 0;
    
    while(j < appointments.size()) {
      
      //find appropriate technicianHour for appointment
      while (appointments.get(j).getStartTime().after(technicianHours.get(i).getEndTime())) { i++; }
      
      boolean strictStart = technicianHours.get(i).getStartTime().before(appointments.get(j).getStartTime());
      boolean strictEnd = appointments.get(j).getEndTime().before(technicianHours.get(i).getEndTime());
      
      //start < a < b < end
      if (strictStart && strictEnd) {
        
        TechnicianHour newTechnicianHour = new TechnicianHour();
        newTechnicianHour.setStartTime(appointments.get(j).getEndTime());
        newTechnicianHour.setEndTime(technicianHours.get(i).getEndTime());
        newTechnicianHour.setDate(appointments.get(j).getDate());
        
        technicianHours.get(i).setEndTime(appointments.get(j).getStartTime());
        technicianHours.add(i+1, newTechnicianHour);
        
      }
      //start < a < b = end
      else if (strictStart) {
        technicianHours.get(i).setEndTime(appointments.get(j).getStartTime());
      }
      //start = a < b < end
      else if (strictEnd) {
        technicianHours.get(i).setStartTime(appointments.get(j).getEndTime());
      }
      //start = a < b = end
      else {
        technicianHours.remove(i);
      }
      
      j++;
    }
    
    return technicianHours;
  }
  
  public List<TechnicianHour> filterByMinDuration(List<TechnicianHour> technicianHours, int minDurationInMin){
     
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    
    List<TechnicianHour> result = new ArrayList<TechnicianHour>();
    
    for (TechnicianHour h : technicianHours) {
      c1.setTime(h.getStartTime());
      c2.setTime(h.getEndTime());
      
      if (c2.getTimeInMillis() - c1.getTimeInMillis() <= 60000*minDurationInMin) {
        result.add(h);
      }
    }
    
    return result;
    
  }
  
  /*
   * 
   * Book Appointment
   * 
   */
  
  //set appointment ID?
  @Transactional
  public Appointment createAppointment(Set<Service> services, Customer customer, Technician technician, java.sql.Time startTime, java.sql.Time endTime, java.sql.Date date) throws IllegalArgumentException{
    
    //sum up service durations
    int sumMin = 0;
    for (Service s : services) {
      sumMin += s.getDuration();
    }
    
    Map<Technician, List<TechnicianHour>> availabilities = getTechnicianAvailableTechnicianHoursByDate(date, sumMin);
    List<TechnicianHour> availabilitiesForTechnician = availabilities.get(technician);
    
    boolean available = false;
    
    for (TechnicianHour h : availabilitiesForTechnician) {
      available = available | (!h.getStartTime().after(startTime) && !h.getEndTime().before(endTime));
    }
    
    if (!available) {
      throw new IllegalArgumentException("Technician not available during specified time slot.");
    }
    
    Appointment appointment = new Appointment();
    appointment.setStartTime(startTime);
    appointment.setEndTime(endTime);
    appointment.setTechnician(technician);
    appointment.setService(services);
    appointment.setCustomer(customer);
    appointment.setDate(date);
    
    //add to customer set?
    //add to technician set?
    //add to appointmentmanager?
    
    appointmentRepository.save(appointment);
    
    return appointment;
  }
  
  @Transactional
  public Appointment deleteAppointment(Integer id) throws IllegalArgumentException {
    Appointment appointment = appointmentRepository.findAppointmentById(id);
    
    if (appointment == null) {
        throw new IllegalArgumentException("Specified appointment does not exist");
    }
    
    appointmentRepository.delete(appointment);
    return appointment;
  }
  
  @Transactional
  //Organized by technician
  //Filtered by minimum duration
  public Map<Technician, List<TechnicianHour>> getTechnicianAvailableTechnicianHoursByDate(java.sql.Date date, int minDurationInMin) {
    
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
  
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
