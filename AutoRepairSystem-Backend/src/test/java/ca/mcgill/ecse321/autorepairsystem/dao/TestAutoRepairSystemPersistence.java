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
import org.junit.jupiter.api.BeforeEach;
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
	
	//User Constants
	private static final String USERNAME = "testUsername";
	private static final String PASSWORD = "testPassword";
	private static final String EMAIL = "testEmail";
	private static final String NAME = "testName";
	private static final int AMOUNTOWED = 20;
	
	//Appointment Constants
	private static final Integer APPOINTMENTID = 101;
	private static final Time APPOINTMENTSTART = Time.valueOf("12:59:59");
	private static final Time APPOINTMENTEND = Time.valueOf("23:59:59");
	
	//WorkItem Constants
	private static final String SERVICENAME = "testService";
	private static final int SERVICEDURATION = 20;
	private static final int SERVICECOST = 20;
	
	//WorkHour Constants
	private static final Integer WORKHOURID = 102;
	private static final Time WORKHOURSTART = Time.valueOf("11:59:59");
	private static final Time WORKHOUREND = Time.valueOf("22:59:59");
	private static final Date WORKHOURDATE = Date.valueOf("2021-01-01");
	
	//WorkBreak Constants
	private static final Integer WORKBREAKID = 103;
	private static final Time WORKBREAKSTART = Time.valueOf("13:59:59");
	private static final Time WORKBREAKEND = Time.valueOf("14:59:59");
	
	
	
	
	
	
	@BeforeEach
	public void clearDatabaseBefore() {
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
	
	@AfterEach
	public void clearDatabaseAfter() {
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
		Administrator administrator = createAdministrator(USERNAME,PASSWORD,NAME,EMAIL);
		
		
		administrator.setUsername(USERNAME);
		administrator.setName(NAME);
		administrator.setPassword(PASSWORD);
		administrator.setEmail(EMAIL);
		
		administratorRepository.save(administrator);
		
		administrator = null;
		
		administrator = administratorRepository.findAdministratorByUsername(USERNAME);
		assertNotNull(administrator);
		assertEquals(USERNAME, administrator.getUsername());
		
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
	int amountOwed = 10;

	Customer customer = new Customer();
	customer.setUsername(username);
	customer.setName(name);
	customer.setPassword(password);
	customer.setEmail(email);
	customer.setAmountOwed(amountOwed);
	
	customerRepository.save(customer);
	
	customer = null;
	customer = customerRepository.findCustomerByUsername(username);
	assertEquals(customer.getUsername(),username);
	}
			
	@Test
	public void testPersistAndLoadAppointment() {
		Time starttime =java.sql.Time.valueOf(LocalTime.of(11, 35));;
		Time endtime =java.sql.Time.valueOf(LocalTime.of(12, 35));;
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Integer id=25;
	
		Technician tech = new Technician ();
		tech.setEmail("techEmail");
		tech.setName("techName");
		tech.setPassword("techPassword");
		tech.setUsername("techUsername");
		technicianRepository.save(tech);
		
		
		//tech.setTechnicianHour(null);
		
		Customer customer = new Customer ();
		customer.setEmail("customerEmail");
		customer.setName("customerName");
		customer.setPassword("customerPassword");
		customer.setUsername("customerUsername");
		customer.setAmountOwed(0);
		customerRepository.save(customer);
		
		Set<WorkItem> workItemSet = new HashSet<WorkItem>();
		WorkItem workItem = new WorkItem();
		workItem.setName("workItemName");
		workItem.setDuration(99);
		workItem.setPrice(999);
		workItemSet.add(workItem);
		workItemRepository.save(workItem);
		
		Appointment appointment = new Appointment ();
		appointment.setCustomer(customer);
		appointment.setTechnician(tech);
		appointment.setWorkItem(workItemSet);
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
		Integer workBreakId = 26;
		
		WorkBreak workBreak = new WorkBreak();
		workBreak.setStartBreak(startbreak);
		workBreak.setEndBreak(endbreak);
		workBreak.setId(workBreakId);
		workBreakRepository.save(workBreak);
		
		workBreak=null;
		
		workBreak=workBreakRepository.findWorkBreakByWorkBreakId(workBreakId);
		assertNotNull(workBreak);
		assertEquals(workBreakId, workBreak.getId());
	}
	
	@Test	
	public void testPersistAndLoadTechnician() {
		
		TechnicianHour techHour = newTechnicianHour(WORKHOURID,WORKHOURSTART,WORKHOUREND,WORKHOURDATE);
		technicianHourRepository.save(techHour);
		
		Set<TechnicianHour> techHourSet = new HashSet<TechnicianHour>();
		techHourSet.add(techHour);
		
		Technician tech = newTechnician(USERNAME,PASSWORD,NAME,EMAIL);
		tech.setTechnicianHour(techHourSet);
		technicianRepository.save(tech);

		tech = null;
		
		tech = technicianRepository.findTechnicianByUsername(USERNAME);
		assertNotNull(tech);
		assertEquals(USERNAME, tech.getUsername());
		
		tech = null;
		
		tech = technicianRepository.find
		
		
	} 
	

	@Test
	public void testPersistAndLoadTechnicianHour() {
		Time startTime =java.sql.Time.valueOf(LocalTime.of(11, 35));;
		Time endTime =java.sql.Time.valueOf(LocalTime.of(12, 35));;
		Date date = java.sql.Date.valueOf("2021-01-01");
		Integer id = 27;
		
		
		TechnicianHour workhour = new TechnicianHour ();
		workhour.setDate(date);
		workhour.setStartTime(startTime);
		workhour.setEndTime(endTime);
		workhour.setId(27);
		workhour.setWorkBreak(null);
		technicianHourRepository.save(workhour);
		
		workhour = null;
		
		workhour = technicianHourRepository.findTechnicianHourById(id);
		assertNotNull(workhour);
		assertEquals(id, workhour.getId());
	}
	

	// Helper Methods
	
	
	private Administrator newAdministrator(String username, String password, String name, String email) {
		Administrator a = new Administrator();
		a.setUsername(username);
		a.setPassword(password);
		a.setName(name);
		a.setEmail(email);
		return a;
	}
	
	private Appointment newAppointment(Integer id, Time startTime, Time endTime, Date date) {
		Appointment a = new Appointment();
		a.setId(id);
		a.setStartTime(startTime);
		a.setEndTime(endTime);
		a.setDate(date);
		return a;
	}
	
	private BusinessHour newBusinessHour(Integer id, Time startTime, Time endTime, Date date) {
		BusinessHour bh = new BusinessHour();
		bh.setId(id);
		bh.setStartTime(startTime);
		bh.setEndTime(endTime);
		bh.setDate(date);
		return bh;
	}
	
	private Customer newCustomer(String username, String password, String name, String email, int amountOwed) {
		Customer c = new Customer();
		c.setUsername(username);
		c.setPassword(password);
		c.setName(name);
		c.setEmail(email);
		c.setAmountOwed(amountOwed);
		return c;
	}
	
	private Technician newTechnician(String username, String password, String name, String email) {
		Technician t = new Technician();
		t.setUsername(username);
		t.setPassword(password);
		t.setName(name);
		t.setEmail(email);
		return t;
	}
	
	private TechnicianHour newTechnicianHour(Integer id, Time startTime, Time endTime, Date date) {
		TechnicianHour th = new TechnicianHour();
		th.setId(id);
		th.setStartTime(startTime);
		th.setEndTime(endTime);
		th.setDate(date);
		return th;
	}
	
	private WorkBreak newWorkBreak(Integer id, Time startTime, Time endTime) {
		WorkBreak wb = new WorkBreak();
		wb.setId(id);
		wb.setStartBreak(startTime);
		wb.setEndBreak(endTime);
		return wb;
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
