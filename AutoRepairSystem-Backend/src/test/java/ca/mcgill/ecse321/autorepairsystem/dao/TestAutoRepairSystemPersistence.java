package ca.mcgill.ecse321.autorepairsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.AppointmentManager;
import ca.mcgill.ecse321.autorepairsystem.model.BusinessHour;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Service;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.model.User;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.WorkHour;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairSystemPersistence {
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AutoRepairSystemRepository autoRepairSystemRepository;
	@Autowired
	private BusinessHourRepository businessHourRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private TechnicianHourRepository technicianHourRepository;
	@Autowired
	private TechnicianRepository technicianRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkBreakRepository workBreakRepository;
	@Autowired
	private WorkHourRepository workHourRepository;
	
	@AfterEach
	public void clearDatabase() {
		workBreakRepository.deleteAll();
		businessHourRepository.deleteAll();
		technicianHourRepository.deleteAll();
		workHourRepository.deleteAll();
		
		appointmentRepository.deleteAll();
		customerRepository.deleteAll();
		technicianRepository.deleteAll();
		administratorRepository.deleteAll();
		
		userRepository.deleteAll();
		serviceRepository.deleteAll();
		//autoRepairSystemRepository.deleteAll();
		
		
	}
	
	@Test
	public void testPersistAndLoadAdministrator() {
		String username = "TestPerson";
		String name = "Henri";
		String password = "123Sesame";
		String email = "mail.mcgill.ca";
		Administrator administrator = new Administrator();
		
		administrator.setUsername(username);
		administrator.setName(name);
		administrator.setPassword(password);
		administrator.setEmail(email);
		
		administratorRepository.save(administrator);
		
		administrator = null;
		
		administrator = administratorRepository.findAdministratorByUsername(username);
		assertNotNull(administrator);
		assertEquals(username, administrator.getUsername());
		
	}
	@Test
	public void testPersistAndLoadService() {
		String name = "tire change";
		int duration = 2;
		int price = 30;
		
		Service service = new Service (); 
		
		service.setName(name);
		service.setDuration(duration);
		service.setPrice(price);
	
		serviceRepository.save(service);
	
		service=null;
		
		service=serviceRepository.findServiceByname(name);
		assertNotNull(service);
		assertEquals(name, service.getName());
		
		
	}
	
	@Test
	public void testPersistAndLoadCustomer() {

	String username = "TestPerson";
	String name = "Henri";
	String password = "123Sesame";
	String email = "mail.mcgill.ca";
	
	String username2 = "TestTechnician";
	String name2 = "TechnicianName";
	String password2 = "1234Sesame";
	String email2 = "mail.mcgill.ca2";
	
	Date date = getCurrentDate();
	Time startTime = getCurrentTime();
	LocalTime localtime = startTime.toLocalTime();
	localtime = localtime.plusMinutes(60);
	Time endTime = java.sql.Time.valueOf(localtime.toString());
	
	
	Set<Appointment> appointments = new HashSet<>();
	Appointment testAppointment = new Appointment();
	testAppointment.setId(321);
	appointments.add(testAppointment);
	Customer customer = new Customer();
	customer.setUsername(username);
	customer.setName(name);
	customer.setPassword(password);
	customer.setEmail(email);
	customer.setAppointment(appointments);
	
	
	Technician technician = new Technician();
	technician.setUsername(username2);
	technician.setName(name2);
	technician.setPassword(password2);
	technician.setEmail(email2);
	technician.setAppointment(appointments);
	
	Service service = new Service();
	service.setName("service1");
	service.setDuration(10);
	service.setPrice(100);
	Set<Service> services = new HashSet<>();
	services.add(service);
	
	testAppointment.setCustomer(customer);
	testAppointment.setTechnician(technician);
	testAppointment.setService(services);
	testAppointment.setDate(date);
	testAppointment.setStartTime(startTime);
	testAppointment.setEndTime(endTime);

	userRepository.save(customer);
	appointmentRepository.save(testAppointment);
	customerRepository.save(customer);
	technicianRepository.save(technician);
	System.out.println("Test1" + customer.getAppointment().toString());
	
	customer = null;
	//testAppointment = null;
	customer = customerRepository.findCustomerByUsername(username);
	assertNotNull(customer);
	assertEquals(username, customer.getUsername());
	customer = null;
	customer = customerRepository.findCustomerByAppointment(testAppointment);
	assertNotNull(customer);
	assertEquals(username,customer.getUsername());
	}

	private Date getCurrentDate() {
		long millis=System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		return date;
	}
	
	private Time getCurrentTime() {
		long millis=System.currentTimeMillis();
		java.sql.Time time = new java.sql.Time(millis);
		return time;
	}
	

}
