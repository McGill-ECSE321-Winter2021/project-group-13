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

import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Administrator;

import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;
import ca.mcgill.ecse321.autorepairsystem.dao.CustomerRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.AdministratorRepository;

@ExtendWith(MockitoExtension.class)
public class TestAdministratorService {

	@Mock
	private AdministratorRepository administratorDao;
	@Mock
	private CustomerRepository customerDao;
	
	
	private static final String USERNAME1 = "testAdmin";
    
    private static final String USERNAME2 = "testAdmin2";
    
    
    private static Boolean CUSTOMERDELETE = false;
    private static Boolean ADMINDELETE = false;
    
    @InjectMocks
	private AutoRepairSystemService service;
    
    @BeforeEach
    public void setMockOutput() {
        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME1)) {
            	if(CUSTOMERDELETE == false) {
            		Customer customer = new Customer();
                	customer.setUsername(USERNAME1);
                    return customer;
            	} else {
            		return null;
            	}
            	
            } else {
                return null;
            }
        });
        
        lenient().when(administratorDao.findAdministratorByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME1)) {
            	if(ADMINDELETE == false) {
            		Administrator administrator = new Administrator();
                	administrator.setUsername(USERNAME1);
                    return administrator;
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
        lenient().when(administratorDao.save(any(Administrator.class))).thenAnswer(returnParameterAsAnswer);
        
        CUSTOMERDELETE = false;
        lenient().doAnswer(invocation -> {
            if(((Customer) invocation.getArgument(0)).getUsername().equals(USERNAME1)) {
            	CUSTOMERDELETE = true;
            }
            return null;
        }).when(customerDao).delete(any(Customer.class));
        
        ADMINDELETE = false;
        lenient().doAnswer(invocation -> {
            if(((Administrator) invocation.getArgument(0)).getUsername().equals(USERNAME1)) {
            	ADMINDELETE = true;
            }
            return null;
        }).when(administratorDao).delete(any(Administrator.class));
    }
    
	@Test
	public void testMakeAdministrator() {
		Administrator admin = null;
		try {
			admin = service.makeAdministrator(USERNAME1);
		} catch(Exception e) {
		}
		
		assertEquals(USERNAME1,admin.getUsername());
		assertNull(customerDao.findCustomerByUsername(USERNAME1));
	}
	
	@Test
	public void testMakeAdministratorFail() {
		String error = null;
		Administrator admin = null;
		try {
			admin = service.makeAdministrator(null);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(admin);
		assertEquals("Cannot have null username",error);
	}
	
	@Test
	public void testMakeAdministratorFail2() {
		String error = null;

		try {
			service.makeAdministrator(USERNAME2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("Specified customer does not exist",error);
	}
	
	@Test
	public void testGetAdministrator() {
		Administrator admin = null;
		try {
			admin = service.getAdministrator(USERNAME1);
		} catch (Exception e) {
		}
		assertEquals(USERNAME1,admin.getUsername());
	}
	
	@Test
	public void testGetAdministratorFail() {
		String error = null;
		Administrator admin = null;
		try {
			admin = service.getAdministrator(null);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(admin);
		assertEquals("Administrator cannot be found",error);
	}
	
	@Test
	public void testGetAllAdministrators() {
        Set<Administrator> adminSet = new HashSet<Administrator>();
        Administrator testAdmin1 = new Administrator();
        Administrator testAdmin2 = new Administrator();
        
        testAdmin1.setUsername(USERNAME1);
        testAdmin2.setUsername(USERNAME2);
        adminSet.add(testAdmin1);
        adminSet.add(testAdmin2);
        
        lenient().when(administratorDao.findAll()).thenReturn(adminSet);

		List<Administrator> allAdmins = null;
		try {
			allAdmins = service.getAllAdministrators();
		} catch(Exception e) {
			
		}
		
		List<String> usernames = new ArrayList<String>();
		usernames.add(allAdmins.get(0).getUsername());
		usernames.add(allAdmins.get(1).getUsername());
		
		assertTrue(usernames.contains(USERNAME1));
		assertTrue(usernames.contains(USERNAME2));
	}
	
}
