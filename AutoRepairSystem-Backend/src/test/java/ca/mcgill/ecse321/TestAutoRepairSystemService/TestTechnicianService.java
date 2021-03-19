package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autorepairsystem.dao.CustomerRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.EndUserRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.TechnicianRepository;
import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.EndUser;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;

@ExtendWith(MockitoExtension.class)
public class TestTechnicianService {
	
	@Mock
	TechnicianRepository technicianDao;
	@Mock
	CustomerRepository customerDao;
	
	@InjectMocks
	private AutoRepairSystemService service;
	
	private static final String USERNAME1 = "username1";
	private static final String USERNAME2 = "username2";
	private static final String PASSWORD = "password";
	
	private static Boolean CUSTOMERDELETE = false;
	
	@BeforeEach
    public void setMockOutput() {
        lenient().when(technicianDao.findTechnicianByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME1)) {
            	Technician technician = new Technician();
                technician.setUsername(USERNAME1);
                technician.setPassword(PASSWORD);
                technician.setName("RandomName");
                technician.setEmail("email.email@email.email");
                technician.setTechnicianHour(new HashSet<TechnicianHour>());
                return technician; 	
            } else {
                return null;
            }
        });
        
        
        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME1)) {
            	if (!CUSTOMERDELETE) {
            		Customer customer = new Customer();
                    customer.setUsername(USERNAME1);
                    customer.setPassword(PASSWORD);
                    customer.setName("RandomName");
                    customer.setEmail("email.email@email.com");
                    customer.setAmountOwed(0);
                    return customer;
            	}
            	return null;
            } else {
                return null;
            }
        });

        
        CUSTOMERDELETE = false;
        lenient().doAnswer(invocation -> {
            if(((Customer) invocation.getArgument(0)).getUsername().equals(USERNAME1)) {
            	CUSTOMERDELETE = true;
            }
            return null;
        }).when(customerDao).delete(any(Customer.class));
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(technicianDao.save(any(Technician.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	
	@Test
    public void testGetAllTechnicians() {
    	Set<Technician> technicianSet = new HashSet<Technician>();
        Technician testTechnician1 = new Technician();
        Technician testTechnician2 = new Technician();
        
        testTechnician1.setUsername(USERNAME1);
        testTechnician2.setUsername(USERNAME2);
        technicianSet.add(testTechnician1);
        technicianSet.add(testTechnician2);
        
        lenient().when(technicianDao.findAll()).thenReturn(technicianSet);

		List<Technician> allTechnicians = null;
		try {
			allTechnicians = service.getAllTechnicians();
		} catch(Exception e) {
			fail();
		}
		
		List<String> usernames = new ArrayList<String>();
		usernames.add(allTechnicians.get(0).getUsername());
		usernames.add(allTechnicians.get(1).getUsername());
		
		assertTrue(usernames.contains(USERNAME1));
		assertTrue(usernames.contains(USERNAME2));
    }
	
	
	@Test
    public void testGetTechnicianByUsername() {
    	Technician technician = null;
    	try {
    		technician = service.getTechnicianByUsername(USERNAME1);
    	} catch(Exception e) {
    		fail();
    	}
    	assertNotNull(technician);
    	assertEquals(USERNAME1, technician.getUsername());
    }
	
	
	@Test
    public void testGetTechnicianByUsernameInvalidUsername() {
    	Technician technician = null;
    	String error = null;
    	try {
    		technician = service.getTechnicianByUsername(" ");
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(technician);
    	assertEquals("A valid username must be provided", error);
    }
	
	
	@Test
    public void testGetTechnicianByUsernameNullUsername() {
    	Technician technician = null;
    	String error = null;
    	try {
    		technician = service.getTechnicianByUsername(null);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(technician);
    	assertEquals("A valid username must be provided", error);
    }
	
	
	@Test
    public void testGetTechnicianByUsernameNoTechnician() {
    	Technician technician = null;
    	String error = null;
    	try {
    		technician = service.getTechnicianByUsername("unknown");
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(technician);
    	assertEquals("Technician cannot be found.", error);
    }
	
	
	@Test
    public void testMakeTechnician() {
		Technician technician = null;
		try {
			technician = service.makeTechnician(USERNAME1);
		} catch(Exception e) {
		}
		
		assertEquals(USERNAME1,technician.getUsername());
		assertNull(customerDao.findCustomerByUsername(USERNAME1));
    }
	
	
	@Test
    public void testMakeTechnicianNullUsername() {
    	Technician technician = null;
    	String error = null;
    	try {
    		technician = service.makeTechnician(null);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(technician);
    	assertEquals("Must enter a valid username!", error);
    }
	
	
	@Test
    public void testMakeTechnicianInvalidUsername() {
    	Technician technician = null;
    	String error = null;
    	try {
    		technician = service.makeTechnician(" ");
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(technician);
    	assertEquals("Must enter a valid username!", error);
    }
	
	
	@Test
    public void testMakeTechnicianNoCustomer() {
    	Technician technician = null;
    	String error = null;
    	try {
    		technician = service.makeTechnician("Mike");
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(technician);
    	assertEquals("Specified customer does not exist", error);
    }

}
