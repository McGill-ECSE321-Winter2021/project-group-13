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

public class TestAdministratorService {

	@Mock
	private AdministratorRepository administratorDao;
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private UserRepository userDao;
	@Mock
	private TechnicianRepository technicianDao;
	
	private static final String USERNAME = "testAdmin";
    private static final String PASSWORD = "testPassword";
    private static final String EMAIL = "admin@email.com";
    private static final String NAME = "testName";
    
    @InjectMocks
	private AutoRepairSystemService service;
    
    @BeforeEach
    public void setMockOutput() {/*
        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME)) {
            	Customer customer = new Customer();
            	customer.setUsername(USERNAME);
            	customer.setPassword(PASSWORD);
            	customer.setEmail(EMAIL);
            	customer.setName(NAME);
                return customer;
            } else {
                return null;
            }
        });
        /*Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };*/
        //lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        //lenient().when(administratorDao.save(any(Administrator.class))).thenAnswer(returnParameterAsAnswer);
        //lenient().when(technicianDao.save(any(Technician.class))).thenAnswer(returnParameterAsAnswer);
   }
    
	@Test
	public void testMakeAdministrator() {
		String error = null;
		Customer customer = new Customer();
		Administrator admin = null;
		customer.setUsername(USERNAME);
		customer.setPassword(PASSWORD);
		customer.setEmail(EMAIL);
		customer.setName(NAME);
		customerDao.save(customer);
		try {
			admin = service.makeAdministrator(USERNAME);
		} catch(Exception e) {
			error = e.getMessage();
			System.out.println(error);
		}
		
		assertEquals(USERNAME,admin.getName());
		assertNull(customerDao.findCustomerByUsername(USERNAME));
	}
}
