package ca.mcgill.ecse321.autorepairsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.*;

import java.sql.Date;
import java.sql.Time;
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
	private EndUserRepository userRepository;
	@Autowired
	private WorkBreakRepository workBreakRepository;
	@Autowired
	private WorkHourRepository workHourRepository;
	
	//User Constants
	private static final String USERNAME = "testUsername";
	private static final String PASSWORD = "testPassword";
	private static final String EMAIL = "testEmail";
	private static final String NAME = "testName";
	
	private static final String USERNAME2 = "testUsername2";
	private static final String PASSWORD2 = "testPassword2";
	private static final String EMAIL2 = "testEmail2";
	private static final String NAME2 = "testName2";
	
	private static final int AMOUNTOWED = 20;
	
	//Appointment Constants
	private static final Integer APPOINTMENTID = 101;
	private static final Time APPOINTMENTSTART = Time.valueOf("12:59:59");
	private static final Time APPOINTMENTEND = Time.valueOf("23:59:59");
	private static final Date APPOINTMENTDATE = Date.valueOf("2020-01-01");
	
	//WorkItem Constants
	private static final String WORKITEMNAME = "testWorkItem";
	private static final int WORKITEMDURATION = 20;
	private static final int WORKITEMPRICE = 20;
	
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
		
		Administrator administrator = newAdministrator(USERNAME,PASSWORD,NAME,EMAIL);
		administratorRepository.save(administrator);
		
		administrator = null;
		
		administrator = administratorRepository.findAdministratorByUsername(USERNAME);
		assertNotNull(administrator);
		assertEquals(USERNAME, administrator.getUsername());
		
		administratorRepository.delete(administrator);
		assertNull(administratorRepository.findAdministratorByUsername(USERNAME));
		
	}

	@Test
	public void testPersistAndLoadAppointment() {
		
		Technician tech = newTechnician(USERNAME,PASSWORD,NAME,EMAIL);
		technicianRepository.save(tech);
		
		Customer customer = newCustomer(USERNAME2,PASSWORD2,NAME2,EMAIL2,AMOUNTOWED);
		customerRepository.save(customer);
		
		WorkItem workItem = newWorkItem(WORKITEMNAME,WORKITEMDURATION,WORKITEMPRICE);
		Set<WorkItem> workItemSet = new HashSet<WorkItem>();
		workItemSet.add(workItem);
		workItemRepository.save(workItem);
		
		Appointment appointment = newAppointment(APPOINTMENTSTART,APPOINTMENTEND,APPOINTMENTDATE);
		appointment.setCustomer(customer);
		appointment.setTechnician(tech);
		appointment.setWorkItem(workItemSet);
		appointmentRepository.save(appointment);
		Integer appointmentId = appointment.getId();
		
		appointment=null;
		
		appointment=appointmentRepository.findAppointmentByCustomer(customer);
		assertNotNull(appointment);
		assertEquals(appointmentId, appointment.getId());
		
		appointment=null;
		appointment=appointmentRepository.findAppointmentByTechnician(tech);
		assertNotNull(appointment);
		assertEquals(appointmentId, appointment.getId());
		
		appointment=null;
		appointment=appointmentRepository.findAppointmentById(appointmentId);
		assertNotNull(appointment);
		assertEquals(appointmentId, appointment.getId());
		
		// Test Delete
		appointmentRepository.delete(appointment);
		assertNull(appointmentRepository.findAppointmentById(appointmentId));
		
		// Test nonCascading Delete
		assertNotNull(technicianRepository.findTechnicianByUsername(USERNAME));
		assertNotNull(customerRepository.findCustomerByUsername(USERNAME2));
		assertNotNull(workItemRepository.findWorkItemByName(WORKITEMNAME));
	}
	
	@Test
	public void testPersistAndLoadBusinessHour() {
		
		WorkBreak workBreak = newWorkBreak(WORKBREAKSTART,WORKBREAKEND);
		Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
		workBreakSet.add(workBreak);
		workBreakRepository.save(workBreak);
		Integer workBreakId = workBreak.getId();
		
		BusinessHour businessHour = newBusinessHour(WORKHOURSTART,WORKHOUREND,WORKHOURDATE);
		
		businessHour.setWorkBreak(workBreakSet);
		businessHourRepository.save(businessHour);
		Integer businessHourId = businessHour.getId();
		
		businessHour = null;
		
		businessHour = businessHourRepository.findBusinessHourById(businessHourId);
		assertNotNull(businessHour);
		assertNotNull(businessHour.getWorkBreak());
		assertEquals(businessHourId, businessHour.getId());
		
		workBreak = null;
		
		workBreak = workBreakRepository.findWorkBreakById(WORKBREAKID);
		assertNotNull(workBreak);
		assertEquals(WORKBREAKID,workBreak.getId());
		
		workBreak = null;
		
		workBreak = toList(businessHour.getWorkBreak()).get(0);
		assertNotNull(workBreak);
		assertEquals(WORKBREAKID,workBreak.getId());

		//Test Delete
		businessHourRepository.delete(businessHour);
		assertNull(businessHourRepository.findBusinessHourById(WORKHOURID));
		
		//Test Cascading Delete
		assertNull(workBreakRepository.findWorkBreakById(WORKBREAKID));
	}
	
	@Test
	public void testPersistAndLoadCustomer() {

		Customer customer = newCustomer(USERNAME,PASSWORD,NAME,EMAIL,AMOUNTOWED);
		customerRepository.save(customer);
		
		customer = null;
		
		customer = customerRepository.findCustomerByUsername(USERNAME);
		assertNotNull(customer);
		assertEquals(USERNAME,customer.getUsername());
		
		customer = null;
		
		customer = customerRepository.findCustomerByEmail(EMAIL);
		assertNotNull(customer);
		assertEquals(USERNAME,customer.getUsername());
		
		// Test Delete
		customerRepository.delete(customer);
		assertNull(customerRepository.findCustomerByUsername(USERNAME));
	
	}
	
	@Test	
	public void testPersistAndLoadTechnician() {
		
		TechnicianHour techHour = newTechnicianHour(WORKHOURID,WORKHOURSTART,WORKHOUREND,WORKHOURDATE);
		Set<TechnicianHour> techHourSet = new HashSet<TechnicianHour>();
		techHourSet.add(techHour);
		
		Technician tech = newTechnician(USERNAME,PASSWORD,NAME,EMAIL);
		tech.setTechnicianHour(techHourSet);
		
		technicianRepository.save(tech);
		technicianHourRepository.save(techHour);
		
		tech = null;
		
		tech = technicianRepository.findTechnicianByUsername(USERNAME);
		assertNotNull(tech);
		assertNotNull(tech.getTechnicianHour());
		assertEquals(USERNAME, tech.getUsername());
		
		tech = null;
		
		tech = technicianRepository.findTechnicianByTechnicianHour(techHour);
		assertNotNull(tech);
		assertNotNull(tech.getTechnicianHour());
		assertEquals(USERNAME, tech.getUsername());
		
		techHour = null;

		techHour = toList(tech.getTechnicianHour()).get(0);
		assertNotNull(techHour);
		assertEquals(WORKHOURID,techHour.getId());
		
		techHour = null;
		
		techHour = technicianHourRepository.findTechnicianHourById(WORKHOURID);
		assertNotNull(techHour);
		assertEquals(WORKHOURID,techHour.getId());

		// Test Delete
		technicianRepository.delete(tech);
		assertNull(technicianRepository.findTechnicianByUsername(USERNAME));
		
		// test Cascading Delete
		assertNull(technicianHourRepository.findTechnicianHourById(WORKHOURID));
		
	} 
	

	@Test
	public void testPersistAndLoadTechnicianHour() {
		
		WorkBreak workBreak = newWorkBreak(WORKBREAKID,WORKBREAKSTART,WORKBREAKEND);
		Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
		workBreakSet.add(workBreak);
		workBreakRepository.save(workBreak);
		
		TechnicianHour techHour = newTechnicianHour(WORKHOURID,WORKHOURSTART,WORKHOUREND,WORKHOURDATE);
		
		techHour.setWorkBreak(workBreakSet);
		technicianHourRepository.save(techHour);
		
		techHour = null;
		
		techHour = technicianHourRepository.findTechnicianHourById(WORKHOURID);
		assertNotNull(techHour);
		assertNotNull(techHour.getWorkBreak());
		assertEquals(WORKHOURID, techHour.getId());
		
		workBreak = null;
		
		workBreak = workBreakRepository.findWorkBreakById(WORKBREAKID);
		assertNotNull(workBreak);
		assertEquals(WORKBREAKID, workBreak.getId());
		
		workBreak = null;
		
		workBreak = toList(techHour.getWorkBreak()).get(0);
		assertNotNull(workBreak);
		assertEquals(WORKBREAKID,workBreak.getId());
		
		//Test Delete
		technicianHourRepository.delete(techHour);
		assertNull(technicianHourRepository.findTechnicianHourById(WORKHOURID));
		
		//Test Cascading Delete
		assertNull(workBreakRepository.findWorkBreakById(WORKBREAKID));
	}
	
	@Test
	public void testPersistAndLoadWorkBreak() {
		
		WorkBreak workBreak = newWorkBreak(WORKBREAKID,WORKBREAKSTART,WORKBREAKEND);
		workBreakRepository.save(workBreak);
		
		workBreak=null;
		
		workBreak=workBreakRepository.findWorkBreakById(WORKBREAKID);
		assertNotNull(workBreak);
		assertEquals(WORKBREAKID, workBreak.getId());
		
		//Test Delete
		workBreakRepository.delete(workBreak);
		assertNull(workBreakRepository.findWorkBreakById(WORKBREAKID));
		
	}
	
	@Test
	public void testPersistAndLoadWorkItem() {
		
		WorkItem workItem = newWorkItem(WORKITEMNAME,WORKITEMDURATION,WORKITEMPRICE);
		workItemRepository.save(workItem);
	
		workItem=null;
		
		workItem=workItemRepository.findWorkItemByName(WORKITEMNAME);
		assertNotNull(workItem);
		assertEquals(WORKITEMNAME, workItem.getName());
		
		// Test Delete
		workItemRepository.delete(workItem);
		assertNull(workItemRepository.findWorkItemByName(WORKITEMNAME));
		
	}

	// Helper Methods
	
	
	private Administrator newAdministrator(String username, String password, String name, String email) {
		Administrator admin = new Administrator();
		admin.setUsername(username);
		admin.setPassword(password);
		admin.setName(name);
		admin.setEmail(email);
		return admin;
	}
	
	private Appointment newAppointment(Time startTime, Time endTime, Date date) {
		Appointment apt = new Appointment();
		apt.setStartTime(startTime);
		apt.setEndTime(endTime);
		apt.setDate(date);
		return apt;
	}
	
	private BusinessHour newBusinessHour(Time startTime, Time endTime, Date date) {
		BusinessHour businessHour = new BusinessHour();
		businessHour.setStartTime(startTime);
		businessHour.setEndTime(endTime);
		businessHour.setDate(date);
		return businessHour;
	}
	
	private Customer newCustomer(String username, String password, String name, String email, int amountOwed) {
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setName(name);
		customer.setEmail(email);
		customer.setAmountOwed(amountOwed);
		return customer;
	}
	
	private Technician newTechnician(String username, String password, String name, String email) {
		Technician technician = new Technician();
		technician.setUsername(username);
		technician.setPassword(password);
		technician.setName(name);
		technician.setEmail(email);
		return technician;
	}
	
	private TechnicianHour newTechnicianHour(Time startTime, Time endTime, Date date) {
		TechnicianHour technicianHour = new TechnicianHour();
		technicianHour.setStartTime(startTime);
		technicianHour.setEndTime(endTime);
		technicianHour.setDate(date);
		return technicianHour;
	}
	
	private WorkBreak newWorkBreak(Time startTime, Time endTime) {
		WorkBreak workBreak = new WorkBreak();
		workBreak.setStartBreak(startTime);
		workBreak.setEndBreak(endTime);
		return workBreak;
	}
	
	private WorkItem newWorkItem(String name, int duration, int price) {
		WorkItem workItem = new WorkItem();
		workItem.setName(name);
		workItem.setDuration(duration);
		workItem.setPrice(price);
		return workItem;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
