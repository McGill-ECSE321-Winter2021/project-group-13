package ca.mcgill.ecse321.autorepairsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	

}
