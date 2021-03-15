package ca.mcgill.ecse321.autorepairsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
	UserRepository userRepository;
	@Autowired
	AdministratorRepository administratorRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
    TechnicianHourRepository technicianHourRepository;
	@Autowired
	AppointmentRepository appointmentRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	WorkHourRepository workHourRepository;
	@Autowired
	WorkBreakRepository workBreakRepository;
	  
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
      
      //set appointment ID?
      @Transactional
      public Appointment createAppointment(Set<ca.mcgill.ecse321.autorepairsystem.model.Service> services, Customer customer, Technician technician, java.sql.Time startTime, java.sql.Time endTime, java.sql.Date date) throws IllegalArgumentException{
        
        //sum up service durations
        int sumMin = 0;
        for (ca.mcgill.ecse321.autorepairsystem.model.Service s : services) {
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
	  
	  //helper methods
      /* Assumptions:
       * -breaks are within technical hours time
       * -appointments are within technical hours time
       * -Service.getDuration() in minutes
       * 
       * Not assumed:
       * -no overlap between breaks
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
	  
	  //Organized by technician
	  public Map<Technician, List<TechnicianHour>> getTechnicianHoursByDate(java.sql.Date date) {
	    
	    List<TechnicianHour> technicianHoursByDate = technicianHourRepository.findByDate(date);
	    
	    return technicianHoursByDate.stream().collect(Collectors.groupingBy(TechnicianHour::getTechnician));
	  }
	  
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
	
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	//Log-in generic user...either customer, technician, or admin
	@Transactional
	public User signIn(String userID, String password) throws IllegalArgumentException {
		User user = userRepository.findById(userID).orElse(null);
		if(user == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		} else if(user.getPassword().equals(password) == false) {
			throw new IllegalArgumentException("Password is wrong!.");
		}
		
		return user;
	}
		
		
	//Customer service methods
	
	@Transactional
	public Customer createCustomer(String username, String password, String name, String email) throws IllegalArgumentException {
		
		if (customerRepository.findCustomerByUsername(username) != null) {
			throw new IllegalArgumentException("Customer with username " + username + " already exists");
		}
		
		if (username == null || username.isEmpty()) {
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
		Set<Appointment> emptyAppointments = new HashSet<Appointment>();
		customer.setAppointment(emptyAppointments);
		customerRepository.save(customer);
		return customer;
	}
	
	@Transactional
	public Customer getCustomerByUsername(String username) throws IllegalArgumentException {
		Customer customer = customerRepository.findCustomerByUsername(username);
		
		if (customer == null) {
			throw new IllegalArgumentException("No customer exists with username " + username);
		}
		
		return customer;
	}
	
	@Transactional
	public Customer getCustomerByAppointment(Appointment appointment) throws IllegalArgumentException {
		Customer customer = customerRepository.findCustomerByAppointment(appointment);
		
		if (customer == null) {
			throw new IllegalArgumentException("No customer linked to the provided appointment");
		}
		
		return customer;
	}
	
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(customerRepository.findAll());
	}
	
	
	//Admin service methods
	
	@Transactional
	public Administrator makeAdministrator(String username) throws IllegalArgumentException {
		Customer customer = customerRepository.findCustomerByUsername(username);
		
		if (customer ==  null) {
			throw new IllegalArgumentException("Specified customer does not exist");
		}
		
		customerRepository.delete(customer);
		Administrator administrator = new Administrator();
		administrator.setUsername(username);
		administrator.setPassword(customer.getPassword());
		administrator.setName(customer.getName());
		administrator.setEmail(customer.getEmail());
		administratorRepository.save(administrator);
		return administrator;
	}

	
	@Transactional
	public Administrator getAdministrator(String username) throws IllegalArgumentException {
		Administrator administrator = administratorRepository.findAdministratorByUsername(username);
		
		if (administrator == null) {
			throw new IllegalArgumentException("Administrator cannot be found");
		}
		
		return administrator;
	}
	
	@Transactional
	public List<Administrator> getAllAdministrators() {
		return toList(administratorRepository.findAll());
	}
	
	
	//User service methods
	
	@Transactional
	public User getUser(String userId) throws IllegalArgumentException {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		}
		return user;
	}

	@Transactional
	public User updateUser(String userId, String password, String name, String email) throws IllegalArgumentException {
		
		if (nonValidString(userId)) {
			throw new IllegalArgumentException("Must enter a proper username!");
		}
		
		User user = userRepository.findById(userId).orElse(null);
		
		if (user == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		}
			
		if (nonValidString(email)) {
			throw new IllegalArgumentException("Must enter a valid email!");
		}
		
		if ((!email.equals(user.getEmail())&&(userRepository.findUserByEmail(email) != null))) {
			throw new IllegalArgumentException("Email already exists!");
		}
		
		if (nonValidString(password)) {
			throw new IllegalArgumentException("Must enter a valid password!");
		}
		
		if (nonValidString(name)) {
			throw new IllegalArgumentException("Must enter a valid name!");
		}
		

		user.setUsername(userId);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		
		userRepository.save(user);
		return user;
	}
	
	@Transactional
	public User deleteUser(String username) throws IllegalArgumentException {
		
		if (nonValidString(username)) {
			throw new IllegalArgumentException("Must enter a proper username!");
		}
		
		User user = userRepository.findUserByUsername(username);
		
		if (user == null) {
			throw new IllegalArgumentException("Specified user does not exist");
		}
		
		userRepository.delete(user);
		return user;
	}
	
	@Transactional
	public List<User> getAllUsers() {
		return toList(userRepository.findAll());
	}
	
	
	//Technician service methods
	
	@Transactional
	public Technician createTechnician(String username, String password, String name, String email) throws IllegalArgumentException {
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A valid username must be provided");
		}
		
		if (technicianRepository.findTechnicianByUsername(username) != null) {
			throw new IllegalArgumentException("Technician with username " + username + " already exists");
		}
		
		
		if (password == null || password == "") {
			throw new IllegalArgumentException("A valid password must be provided");
		}
		
		if (name == null || name == "") {
			throw new IllegalArgumentException("A valid name must be provided");
		}
		
		if (email == null || email == "") {
			throw new IllegalArgumentException("An valid email must be provided");
		}
		
		if (userRepository.findUserByEmail(email) != null) {
			throw new IllegalArgumentException("The email already exits!");
		}
		
		Technician technician = new Technician();
		technician.setUsername(username);
		technician.setPassword(password);
		technician.setName(name);
		technician.setEmail(email);
		
		Set<Appointment> emptyAppointments = new HashSet<Appointment>();
		technician.setAppointment(emptyAppointments);
		Set<TechnicianHour> emptyTechnicianHour = new HashSet<TechnicianHour>();
		technician.setTechnicianHour(emptyTechnicianHour);
		
		technicianRepository.save(technician);
		return technician;
	}
	
	@Transactional
	public Technician getTechnicianByUsername(String username) throws IllegalArgumentException{
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A valid username must be provided");
		}
		
		Technician technician = technicianRepository.findTechnicianByUsername(username);
		
		if (technician == null) {
			throw new IllegalArgumentException("Technician cannot be found.");
		}
		
		return technician;
	}
	
	@Transactional
	public Technician getTechnicianByAppointment(Appointment appointment) {
		Technician technician = technicianRepository.findTechnicianByAppointment(appointment);
		
		if (technician == null) {
			throw new IllegalArgumentException("Technician cannot be found.");
		}
		
		return technician;
	}
	
	@Transactional
	public Technician getTechnicianByTechnicianHour(TechnicianHour technicianHour) {
		Technician technician = technicianRepository.findTechnicianByTechnicianHour(technicianHour);
		
		if (technician == null) {
			throw new IllegalArgumentException("Technician cannot be found.");
		}
		
		return technician;
	}
	
	
	@Transactional
	public List<Technician> getAllTechnicians(){
		return toList(technicianRepository.findAll());
	}
	
	@Transactional
	public Technician deleteTechnician(String username) throws IllegalArgumentException{
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A valid username must be provided");
		}
		
		Technician technician = technicianRepository.findTechnicianByUsername(username);
		
		if (technician == null) {
			throw new IllegalArgumentException("Technician cannot be found.");
		}
		
		technicianRepository.delete(technician);
		
		return technician;
	}
	
	
	@Transactional
	public Technician updateTechnician(String username, String password, String name, String email) throws IllegalArgumentException {
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A valid username must be provided");
		}
		
		Technician technician = technicianRepository.findTechnicianByUsername(username);
		
		if (technician == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		}
		
		if (password == null || password == "") {
			throw new IllegalArgumentException("A valid password must be provided");
		}
		
		if (name == null || name == "") {
			throw new IllegalArgumentException("A valid name must be provided");
		}
		
		if (email == null || email == "") {
			throw new IllegalArgumentException("An valid email must be provided");
		}
		
		if ((!email.equals(technician.getEmail())&&(userRepository.findUserByEmail(email) != null))) {
			throw new IllegalArgumentException("Email already exists!");
		}
		
		
		technician.setUsername(username);
		technician.setPassword(password);
		technician.setName(name);
		technician.setEmail(email);
		
		technicianRepository.save(technician);
		return technician;
	}
	
	@Transactional
	public Technician updateTechnicianAppointment(String username, Set<Appointment> appointment) throws IllegalArgumentException {
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A valid username must be provided");
		}
		
		Technician technician = technicianRepository.findTechnicianByUsername(username);
		
		if (technician == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		}

		if (appointment == null) {
			throw new IllegalArgumentException("Valid appointments must be provided!");
		}
		
		technician.setAppointment(appointment);
		
		technicianRepository.save(technician);
		return technician;
	}
	
	@Transactional
	public Technician updateTechnicianTechnicianHour(String username, Set<TechnicianHour> technicianHour) throws IllegalArgumentException {
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A valid username must be provided");
		}
		
		Technician technician = technicianRepository.findTechnicianByUsername(username);
		
		if (technician == null) {
			throw new IllegalArgumentException("Username cannot be found!");
		}
		
		if (technicianHour == null) {
			throw new IllegalArgumentException("Valid technician hours must be provided!");
		}

		technician.setTechnicianHour(technicianHour);
		
		technicianRepository.save(technician);
		return technician;
	}
	
	//Appointment service methods
	
	@Transactional
	public Set<Appointment> getAppointmentsAttendedByCustomer(Customer customer) {
		Set<Appointment> appointmentsAttendedByCustomer = new HashSet<Appointment>();
		for (Appointment a : appointmentRepository.findByCustomer(customer)) {
			appointmentsAttendedByCustomer.add(a);
		}
		return appointmentsAttendedByCustomer;
	}
	
	//Service service methods
	
	@Transactional
	public ca.mcgill.ecse321.autorepairsystem.model.Service createService(String name, int duration, int price) throws IllegalArgumentException {
		if (name == null || name == "" ) {
			throw new IllegalArgumentException("A valid service name must be provide!");
		}
		
		if (serviceRepository.findServiceByName(name) != null) {
			throw new IllegalArgumentException("Service with username " + name + " already exists");
		}
		
		if (duration < 0) {
			throw new IllegalArgumentException("Duration cannot be less than zero!");
		}
		
		if(price < 0) {
			throw new IllegalArgumentException("Price cannot be less than zero!");
		}
		
		return null;
		
		/*Service service = new Service();
		service.setName(name);
		service.setDuration(duration);
		service.setPrice(price);
		
		serviceRepository.save(service);
		return service;*/
	}
	 //WorkHour service methods
	
	@Transactional
	public WorkHour getWorkHour(Integer id) throws IllegalArgumentException {
		WorkHour workHour = workHourRepository.findWorkHourById(id);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Work Hour cannot be found!");
		}
		return workHour;
	}
	
	
	@Transactional
	public WorkHour updateWorkHour(Integer id, Time startTime, Time endTime, Date date) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(id);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Work Hour cannot be found");
		}
		
		if (date == null) {
			throw new IllegalArgumentException("A valid date must be provided!");
		}
		
		if (startTime == null || startTime.after(endTime) == true) {
			throw new IllegalArgumentException("A valid start time must be provided! (non-empty or before end time)");
		}
		
		if (endTime == null || endTime.before(startTime) == true) {
			throw new IllegalArgumentException("A valid end time must be provided! (non-empty or after start time)");
		}
		
		workHour.setDate(date);
		workHour.setStartTime(startTime);
		workHour.setEndTime(endTime);
		workHourRepository.save(workHour);
		
		
		return workHour;
	}
	
	@Transactional
	public WorkHour updateWorkHourWorkBreak(Integer id, Set<WorkBreak> workBreak) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(id);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Work Hour cannot be found");
		}
		
		if (workBreak == null) {
			throw new IllegalArgumentException("A valid work break must be provided!");
		}
		
		workHour.setWorkBreak(workBreak);
		workHourRepository.save(workHour);
		
		return workHour;
	}
	
	@Transactional
	public WorkHour deleteWorkHour(Integer id) throws IllegalArgumentException {
		
		if (id == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(id);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
		}
		
		workHourRepository.delete(workHour);
		return workHour;
	}
	
	@Transactional
	public List<WorkHour> getAllWorkHours(){
		return toList(workHourRepository.findAll());
	}
	
	// WorkBreak service methods
	@Transactional
	public WorkBreak createWorkBreak(Integer workHourId, Time startTime, Time endTime) throws IllegalArgumentException {
		
		if (workHourId == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		if (startTime.toLocalTime().isAfter(endTime.toLocalTime())) {
			throw new IllegalArgumentException("Start time must be before end time");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(workHourId);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
		}
		
		Set<WorkBreak> workBreaks = workHour.getWorkBreak();
		
		// Make sure work break does not overlap with existing work break
		for(WorkBreak w : workBreaks) {
			
			if(!(startTime.toLocalTime().isBefore(w.getEndBreak().toLocalTime()))) {
				continue;
			} else if(!(w.getStartBreak().toLocalTime().isBefore(endTime.toLocalTime()))) {
				continue;
			} else {
				throw new IllegalArgumentException("Work Hour overlaps with existing work hour");
			}
		}
		
		WorkBreak workBreak = new WorkBreak();
		workBreak.setStartBreak(startTime);
		workBreak.setEndBreak(endTime);
		
		workBreaks.add(workBreak);
		
		workHour.setWorkBreak(workBreaks);
		workHourRepository.save(workHour);
		workBreakRepository.save(workBreak);
		
		return workBreak;
	}
	
	@Transactional
	public WorkBreak getWorkBreak(Integer workHourId, Time startTime) throws IllegalArgumentException {
		
		if (workHourId == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(workHourId);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
		}

		WorkBreak workBreak = workBreakRepository.findByWorkHourAndStartBreak(workHour, startTime);
		
		if(workBreak == null) {
			throw new IllegalArgumentException("Specified Work Break doesn't exist for specified Work Hour");
		}
		
		return workBreak;
		
	}
	
	@Transactional
	public List<WorkBreak> getAllWorkBreaks(Integer workHourId) throws IllegalArgumentException {
		
		if (workHourId == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(workHourId);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
		}
		
		return toList(workHour.getWorkBreak());
	}
	
	@Transactional
	public WorkBreak updateWorkBreak(Integer workHourId, Time startTime, Time newStartTime, Time newEndTime) {
		
		if (workHourId == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(workHourId);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
		}
		
		Set<WorkBreak> workBreaks = workHour.getWorkBreak();
		WorkBreak workBreak = workBreakRepository.findByWorkHourAndStartBreak(workHour, startTime);
		
		// Make sure work break does not overlap with existing work break (not including the work break being updated)
		for (WorkBreak w : workBreaks) {
			if(w.getStartBreak().toLocalTime().equals(workBreak.getStartBreak().toLocalTime())) {
				continue;
			} else if(!(newStartTime.toLocalTime().isBefore(w.getEndBreak().toLocalTime()))) {
				continue;
			} else if(!(w.getStartBreak().toLocalTime().isBefore(newEndTime.toLocalTime()))) {
				continue;
			} else {
				throw new IllegalArgumentException("Updated Work Hour overlaps with existing work hour");
			}
		}
		
		workBreaks.remove(workBreak);
		workBreak.setStartBreak(newStartTime);
		workBreak.setEndBreak(newEndTime);
		
		workBreaks.add(workBreak);
		
		workHour.setWorkBreak(workBreaks);
		workHourRepository.save(workHour);
		workBreakRepository.save(workBreak);
		return workBreak;
		
	}
	
	@Transactional
	public WorkBreak DeleteWorkBreak(Integer workHourId, Time startTime) throws IllegalArgumentException {
		
		if (workHourId == null) {
			throw new IllegalArgumentException("A valid work hour ID must be provided!");
		}
		
		WorkHour workHour = workHourRepository.findWorkHourById(workHourId);
		
		if (workHour == null) {
			throw new IllegalArgumentException("Specified Work Hour doesn't exist!");
		}
		
		Set<WorkBreak> workBreaks = workHour.getWorkBreak();
		
		for (WorkBreak w : workBreaks) {
			if(w.getStartBreak().equals(startTime)) {
				workBreaks.remove(w);
				workBreakRepository.delete(w);
				workHour.setWorkBreak(workBreaks);
				workHourRepository.save(workHour);
				return(w);
			}
		}
		
		throw new IllegalArgumentException("Specified Work Break doesn't exist for specified Work Hour");
		
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
