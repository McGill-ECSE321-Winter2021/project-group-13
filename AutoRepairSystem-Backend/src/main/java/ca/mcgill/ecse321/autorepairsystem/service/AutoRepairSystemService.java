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

import ca.mcgill.ecse321.autorepairsystem.dao.AdministratorRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.CustomerRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.UserRepository;
import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.User;

@Service
public class AutoRepairSystemService {
	
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AdministratorRepository administratorRepository;
	
	
	//Customer service methods
	
	@Transactional
	public Customer createCustomer(String username, String password, String name, String email) throws IllegalArgumentException {
		
		if (customerRepository.findCustomerByUsername(username) != null) {
			throw new IllegalArgumentException("Customer with username " + username + " already exists");
		}
		
		if (username == null || username == "") {
			throw new IllegalArgumentException("A username must be provided");
		}
		
		if (password == null || password == "") {
			throw new IllegalArgumentException("A password must be provided");
		}
		
		if (name == null || name == "") {
			throw new IllegalArgumentException("A name must be provided");
		}
		
		if (email == null || email == "") {
			throw new IllegalArgumentException("An email must be provided");
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
	public Customer getCustomerByUsername(String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		return customer;
	}
	
	@Transactional
	public Customer getCustomerByAppointment(Appointment appointment) {
		Customer customer = customerRepository.findCustomerByAppointment(appointment);
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
		
		Administrator administrator = new Administrator();
		administrator.setUsername(username);
		administrator.setPassword(customer.getPassword());
		administrator.setName(customer.getName());
		administrator.setEmail(customer.getEmail());
		administratorRepository.save(administrator);
		customerRepository.delete(customer);
		return administrator;
	}

	
	@Transactional
	public Administrator getAdministrator(String username) {
		Administrator administrator = administratorRepository.findAdministratorByUsername(username);
		return administrator;
	}
	
	@Transactional
	public List<Administrator> getAllAdministrators() {
		return toList(administratorRepository.findAll());
	}
	
	
	//User service methods
	
	@Transactional
	public User updateUser(String prevUsername, String username, String password, String name, String email) throws IllegalArgumentException {
		User user = userRepository.findUserByUsername(prevUsername);
		
		if (user == null) {
			throw new IllegalArgumentException("There is no existing account with username " + prevUsername);
		}
		
		if (userRepository.findUserByUsername(username) != null) {
			throw new IllegalArgumentException("Account with username " + username + " already exists");
		}
		
		if (username == null) {
			throw new IllegalArgumentException("A username must be provided");
		}
		
		if (password == null) {
			throw new IllegalArgumentException("A password must be provided");
		}
		
		if (name == null) {
			throw new IllegalArgumentException("A name must be provided");
		}
		
		if (email == null) {
			throw new IllegalArgumentException("An email must be provided");
		}
		
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		userRepository.save(user);
		return user;
	}
	
	@Transactional
	public User deleteUser(String username) throws IllegalArgumentException {
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
	
	
	//Helper methods
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
