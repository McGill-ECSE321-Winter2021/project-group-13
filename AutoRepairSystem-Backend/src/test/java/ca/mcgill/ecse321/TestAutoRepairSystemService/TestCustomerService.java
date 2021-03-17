package ca.mcgill.ecse321.TestAutoRepairSystemService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import ca.mcgill.ecse321.autorepairsystem.model.*;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;
import ca.mcgill.ecse321.autorepairsystem.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

	@Mock
	private CustomerRepository customerDao;
	@Mock
	private EndUserRepository userDao;
	
	private static final String USERNAME1 = "testCustomer1";
	private static final String PASSWORD1 = "testPassword1";
	private static final String NAME1 = "testName1";
	private static final String EMAIL1 = "testEmail1";
    
    private static final String USERNAME2 = "testCustomer2";
    private static final String PASSWORD2 = "testPassword2";
	private static final String NAME2 = "testName2";
	private static final String EMAIL2 = "testEmail2";
    
    private static Boolean CUSTOMERDELETE = false;
    
    @InjectMocks
	private AutoRepairSystemService service;
    
    @BeforeEach
    public void setMockOutput() {
        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME1)) {
            	if(CUSTOMERDELETE == false) {
            		Customer customer = new Customer();
                	customer.setUsername(USERNAME1);
                	customer.setPassword(PASSWORD1);
                	customer.setName(NAME1);
                	customer.setEmail(EMAIL1);
                    return customer;
            	} else {
            		return null;
            	}
            	
            } else {
                return null;
            }
        });
        
        lenient().when(customerDao.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(EMAIL1)) {
            	if(CUSTOMERDELETE == false) {
            		Customer customer = new Customer();
                	customer.setUsername(USERNAME1);
                	customer.setPassword(PASSWORD1);
                	customer.setName(NAME1);
                	customer.setEmail(EMAIL1);
                    return customer;
            	} else {
            		return null;
            	}
            	
            } else {
                return null;
            }
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
       
        
        lenient().doAnswer(invocation -> {
            if(((Customer) invocation.getArgument(0)).getUsername().equals(USERNAME1)) {
            	CUSTOMERDELETE = true;
            	System.out.println("Hello World");
            }
            return null;
        }).when(customerDao).delete(any(Customer.class));
    }
    
    @BeforeEach
    public void resetCustomerDelete() {
    	CUSTOMERDELETE = false;
    }
    
    @Test
    public void testCreateCustomer() {
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(USERNAME2, PASSWORD2, NAME2, EMAIL2);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(USERNAME2,customer.getUsername());
    }
    
    @Test
    public void testCreateCustomerExistingUsername() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(USERNAME1, PASSWORD2, NAME2, EMAIL2);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"Customer with username " + USERNAME1 + " already exists");
    }
    
    @Test
    public void testCreateCustomerNullUsername() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(null, PASSWORD2, NAME2, EMAIL2);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"A username must be provided");
    }
    
    @Test
    public void testCreateCustomerNullPassword() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(USERNAME2, null, NAME2, EMAIL2);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"A password must be provided");
    }
    
    @Test
    public void testCreateCustomerNullName() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(USERNAME2, PASSWORD2, null, EMAIL2);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"A name must be provided");
    }
    
    @Test
    public void testCreateCustomerExistingEmail() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(USERNAME2, PASSWORD2, NAME2, EMAIL1);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"Account with the provided email already exists");
    }
    
    @Test
    public void testCreateCustomerNullEmail() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.createCustomer(USERNAME2, PASSWORD2, NAME2, null);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"An email must be provided");
    }
    
    @Test
    public void testGetCustomerByUsername() {
    	Customer customer = null;
    	try {
    		customer = service.getCustomerByUsername(USERNAME1);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(USERNAME1,customer.getUsername());
    }
    
    @Test
    public void testGetCustomerByUsernameFail() {
    	String error = null;
    	Customer customer = null;
    	try {
    		customer = service.getCustomerByUsername(USERNAME2);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals(error,"No customer exists with username " + USERNAME2);
    }
    
    @Test
    public void testGetAllCustomer() {
    	Set<Customer> customerSet = new HashSet<Customer>();
        Customer testCustomer1 = new Customer();
        Customer testCustomer2 = new Customer();
        
        testCustomer1.setUsername(USERNAME1);
        testCustomer2.setUsername(USERNAME2);
        customerSet.add(testCustomer1);
        customerSet.add(testCustomer2);
        
        lenient().when(customerDao.findAll()).thenReturn(customerSet);

		List<Customer> allCustomers = null;
		try {
			allCustomers = service.getAllCustomers();
		} catch(Exception e) {
			
		}
		
		List<String> usernames = new ArrayList<String>();
		usernames.add(allCustomers.get(0).getUsername());
		usernames.add(allCustomers.get(1).getUsername());
		
		assertTrue(usernames.contains(USERNAME1));
		assertTrue(usernames.contains(USERNAME2));
    }
}