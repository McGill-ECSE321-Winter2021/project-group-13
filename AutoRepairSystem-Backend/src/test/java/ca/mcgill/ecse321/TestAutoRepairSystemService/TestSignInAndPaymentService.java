package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.EndUser;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;

@ExtendWith(MockitoExtension.class)
public class TestSignInAndPaymentService {
	
	@Mock
	CustomerRepository customerDao;
	@Mock
	EndUserRepository endUserDao;
	
	@InjectMocks
	private AutoRepairSystemService service;
	
	private static final String USERNAME = "Jack";
	private static final String PASSWORD = "apassword";
	
	@BeforeEach
    public void setMockOutput() {
        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME)) {
            	Customer customer = new Customer();
                customer.setUsername(USERNAME);
                customer.setPassword(PASSWORD);
                customer.setName("RandomName");
                customer.setEmail("email.email@email.email");
                customer.setAmountOwed(100);
                return customer; 	
            } else {
                return null;
            }
        });
        
        lenient().when(endUserDao.findEndUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME)) {
            	EndUser endUser = new Customer();
                endUser.setUsername(USERNAME);
                endUser.setPassword(PASSWORD);
                endUser.setName("RandomName");
                endUser.setEmail("email.email@email.email");
                return endUser;
            } else {
                return null;
            }
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(endUserDao.save(any(EndUser.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
    public void testPayment() {
    	Customer customer = null;
    	try {
    		customer = service.payment(USERNAME, 10);
    	} catch(Exception e) {
    		fail();
    	}
    	assertEquals(USERNAME, customer.getUsername());
    	assertEquals(90, customer.getAmountOwed());
    }
	
	
	@Test
    public void testPaymentTooMuch() {
    	Customer customer = null;
    	try {
    		customer = service.payment(USERNAME, 110);
    	} catch(Exception e) {
    		fail();
    	}
    	assertEquals(USERNAME, customer.getUsername());
    	assertEquals(0, customer.getAmountOwed());
    }
	
	
	@Test
	public void testPaymentInvalidUsername() {
		Customer customer = null;
		String error = null;
    	try {
    		customer = service.payment(" ", 110);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals("A valid username must be provided!", error);
	}
	
	
	@Test
	public void testPaymentNullUsername() {
		Customer customer = null;
		String error = null;
    	try {
    		customer = service.payment(null, 110);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals("A valid username must be provided!", error);
	}
	
	
	@Test
	public void testPaymentNoCustomer() {
		Customer customer = null;
		String error = null;
    	try {
    		customer = service.payment("Non-existent-customer-username", 110);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(customer);
    	assertEquals("Specified customer does not exist!", error);
	}
	
	
	@Test
	public void testSignIn() {
		EndUser endUser = null;
    	try {
    		endUser = service.signIn(USERNAME, PASSWORD);
    	} catch(Exception e) {
    		fail();
    	}
    	assertEquals(USERNAME, endUser.getUsername());
	}
	
	
	@Test
	public void testSignInIncorrectUsername() {
		EndUser endUser = null;
		String error = null;
    	try {
    		endUser = service.signIn("Incorrect username", PASSWORD);
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(endUser);
    	assertEquals("Username cannot be found!", error);
	}
	
	
	@Test
	public void testSignInIncorrectPassword() {
		EndUser endUser = null;
		String error = null;
    	try {
    		endUser = service.signIn(USERNAME, "Incorrect password");
    	} catch(Exception e) {
    		error = e.getMessage();
    	}
    	assertNull(endUser);
    	assertEquals("Incorrect password!", error);
	}

}
