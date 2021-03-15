package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autorepairsystem.model.*;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;
import ca.mcgill.ecse321.autorepairsystem.dao.*;

public class TestAutoRepairSystemService {
	
	@Mock
	private AdministratorRepository administratorDao;
	@Mock
	private AppointmentRepository appointmentDao;
	@Mock
	private BusinessHourRepository businessHourDao;
	@Mock
	private CustomerRepository customerDao;
	@Mock 
	private ServiceRepository serviceDao;
	@Mock
	private TechnicianRepository technicianDao;
	@Mock
	private TechnicianHourRepository technicianHourDao;
	@Mock
	private UserRepository userDao;
	@Mock
	private WorkBreakRepository workBreakDao;
	@Mock
	private WorkHourRepository workHourDao;
	
	@InjectMocks
	private AutoRepairSystemService service;
	
	//private static final String 
	//private static final String NONEXISTING_KEY = 
	
	@BeforeEach
	public void setMockOutput() {

	}
	
	// Test Administrator Service Methods
	
	@Test
	public void testMakeCustomer() {
		String name = "testName";
		Customer customer = new Customer();
		customer.setUsername("testName");
		customerDao.save(customer);
		try {
			
		} catch(Exception e) {
		}
	}
	
	
	
	
	
	
}
