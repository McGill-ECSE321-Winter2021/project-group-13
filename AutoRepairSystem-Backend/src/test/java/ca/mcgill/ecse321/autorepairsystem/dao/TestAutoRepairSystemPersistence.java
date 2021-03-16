package ca.mcgill.ecse321.autorepairsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.BusinessHour;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.WorkItem;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairSystemPersistence {
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	//@Autowired
	//private AutoRepairSystemRepository autoRepairSystemRepository;
	@Autowired
	private BusinessHourRepository businessHourRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private WorkItemRepository workItemRepository;
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
		workItemRepository.deleteAll();
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
	public void testPersistAndLoadWorkItem() {
		String name = "tire change";
		int duration = 2;
		int price = 30;
		
		WorkItem workItem = new WorkItem (); 
		
		workItem.setName(name);
		workItem.setDuration(duration);
		workItem.setPrice(price);
	
		workItemRepository.save(workItem);
	
		workItem=null;
		
		workItem=workItemRepository.findWorkItemByName(name);
		assertNotNull(workItem);
		assertEquals(name, workItem.getName());
		
		
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
	
	
	Technician technician = new Technician();
	technician.setUsername(username2);
	technician.setName(name2);
	technician.setPassword(password2);
	technician.setEmail(email2);
	
	WorkItem workItem = new WorkItem();
	workItem.setName("workItem1");
	workItem.setDuration(10);
	workItem.setPrice(100);
	Set<WorkItem> workItems = new HashSet<>();
	workItems.add(workItem);
	
	testAppointment.setCustomer(customer);
	testAppointment.setTechnician(technician);
	testAppointment.setWorkItem(workItems);
	testAppointment.setDate(date);
	testAppointment.setStartTime(startTime);
	testAppointment.setEndTime(endTime);

	userRepository.save(customer);
	appointmentRepository.save(testAppointment);
	customerRepository.save(customer);
	technicianRepository.save(technician);
	
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
			
	@Test
	public void testPersistAndLoadAppointment() {
		Time starttime =java.sql.Time.valueOf(LocalTime.of(11, 35));;
		Time endtime =java.sql.Time.valueOf(LocalTime.of(12, 35));;
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Integer id=25;
		
	
		Technician tech = new Technician ();
		tech.setEmail(null);
		tech.setName(null);
		tech.setPassword(null);
		tech.setTechnicianHour(null);
		tech.setUsername("Ezra1");
		technicianRepository.save(tech);
		
		Customer customer = new Customer ();
		customer.setEmail("Ezra2");
		customer.setName("Ezra3");
		customer.setPassword("Ezra4");
		customer.setUsername("Ezra5");
		customerRepository.save(customer);
		
		Appointment appointment = new Appointment ();
		appointment.setCustomer(customer);
		appointment.setTechnician(tech);
		appointment.setWorkItem(null);;
		appointment.setStartTime(starttime);
		appointment.setEndTime(endtime);
		appointment.setDate(date);
		appointment.setId(id);
		
		appointmentRepository.save(appointment);
		
		appointment=null;
		
		appointment=appointmentRepository.findAppointmentByCustomer(customer);
		assertNotNull(appointment);
		assertEquals(id, appointment.getId());
		
		appointment=null;
		appointment=appointmentRepository.findAppointmentByTechnician(tech);
		assertNotNull(appointment);
		assertEquals(date, appointment.getDate());
		
		appointment=null;
		appointment=appointmentRepository.findAppointmentById(id);
		assertNotNull(appointment);
		assertEquals(id, appointment.getId());
		
		
			
	}
	
	@Test
	public void testPersistAndLoadWorkBreak() {
		Time startbreak =java.sql.Time.valueOf(LocalTime.of(11, 35));;
		Time endbreak =java.sql.Time.valueOf(LocalTime.of(12, 35));;
	
	
		Technician tech = new Technician ();
		tech.setEmail(null);
		tech.setName(null);
		tech.setPassword(null);
		tech.setTechnicianHour(null);
		tech.setUsername("Ezra");
		technicianRepository.save(tech);
		
		TechnicianHour workhour = new TechnicianHour ();
		workhour.setDate(null);
		workhour.setEndTime(null);
		workhour.setId(2);
		workhour.setStartTime(null);
		workhour.setWorkBreak(null);
		technicianHourRepository.save(workhour);
		
		
		WorkBreak workbreak = new WorkBreak ();
		workbreak.setStartBreak(startbreak);
		workbreak.setEndBreak(endbreak);
		
		
		workBreakRepository.save(workbreak);
		
		workbreak=null;
		
		workbreak=workBreakRepository.findWorkBreakByStartBreak(startbreak);
		assertNotNull(workbreak);
		assertEquals(startbreak, workbreak.getStartBreak());
		
		workbreak=null;
		
		workbreak=workBreakRepository.findWorkBreakByWorkHour(workhour);
		assertNotNull(workbreak);
		assertEquals(startbreak, workbreak.getStartBreak());
		
	}
	
	@Test	
	public void testPersistAndLoadTechnician() {
		
		Technician tech = new Technician ();
		tech.setEmail(null);
		tech.setName(null);
		tech.setPassword(null);
		tech.setTechnicianHour(null);
		tech.setUsername("Ezra1");
		technicianRepository.save(tech);
		
		technicianRepository.save(tech);
		tech = null;
		
		tech = technicianRepository.findTechnicianByUsername("Ezra1");
		assertNotNull(tech);
		assertEquals("Ezra1", tech.getUsername());
		
		
	} 
	

	@Test
	public void testPersistAndLoadTechnicianHour() {
		
	
		Technician tech = new Technician ();
		tech.setEmail(null);
		tech.setName(null);
		tech.setPassword(null);
		tech.setTechnicianHour(null);
		tech.setUsername("Ezra");
		technicianRepository.save(tech);
		
		TechnicianHour workhour = new TechnicianHour ();
		workhour.setDate(null);
		workhour.setEndTime(null);
		workhour.setId(2);
		workhour.setStartTime(null);
		workhour.setWorkBreak(null);
		technicianHourRepository.save(workhour);
		
		
		workhour = null;
		
		workhour = technicianHourRepository.findTechnicianHourByTechnician(tech);
		assertNotNull(workhour);
		assertEquals(2, workhour.getId());
		
        workhour = null;
		
		workhour = technicianHourRepository.findTechnicianHourById(2);
		assertNotNull(workhour);
		assertEquals(2, workhour.getId());
		
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
