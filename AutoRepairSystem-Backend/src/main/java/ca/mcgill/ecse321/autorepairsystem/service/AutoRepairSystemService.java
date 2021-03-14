package ca.mcgill.ecse321.autorepairsystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	AppointmentRepository appointmentRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	WorkHourRepository workHourRepository;
	
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
	public Service createService(String name, int duration, int price) throws IllegalArgumentException {
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
