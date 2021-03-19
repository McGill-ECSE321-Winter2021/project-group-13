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
public class TestEndUserService {
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
    
   
    
    @InjectMocks
	private AutoRepairSystemService service;
	
    @BeforeEach
    public void setMockOutput() {
        lenient().when(userDao.findEndUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USERNAME1)) {
      
            		EndUser enduser = new Customer();
                	enduser.setUsername(USERNAME1);
                	enduser.setPassword(PASSWORD1);
                	enduser.setName(NAME1);
                	enduser.setEmail(EMAIL1);
                    return enduser;
            	
            } else {
                return null;
            }
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        lenient().when(userDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void updateEndUser() {
    	EndUser enduser = null;
    	
    	try {
    		enduser = service.updateEndUser(USERNAME1, PASSWORD2, NAME2, EMAIL2);
    	} catch(Exception e) {
    		
    	}
    
    	
    	assertEquals(USERNAME1, enduser.getUsername());
    	assertEquals(PASSWORD2, enduser.getPassword());
    	assertEquals(NAME2, enduser.getName());
    	assertEquals(EMAIL2, enduser.getEmail());
    	
    	
    }
    
    @Test
    public void updateEndUserDoesntExist() {
    	EndUser enduser = null;
    	String error = null;
    	try {
    		enduser = service.updateEndUser(USERNAME2, PASSWORD1, NAME1, EMAIL1);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(enduser);
    	assertEquals(error,"Username cannot be found!");
    }
	
	@Test
	public void testGetEndUserDoesntExist() {
		String error = null;
		EndUser admin = null;
		try {
			admin = service.getEndUser(USERNAME2);
		} catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(admin);
		assertEquals("Username " + USERNAME2 + " cannot be found!",error);
	}
	
	@Test
	public void testGetEndUserExists() {
		EndUser admin = null;
		try {
			admin = service.getEndUser(USERNAME1);
		} catch (Exception e) {
			
		}
		
		assertEquals(USERNAME1,admin.getUsername());
		assertEquals(PASSWORD1, admin.getPassword());
	}
	
	@Test
	public void testGetAllEndUsers() {
        Iterable<EndUser> EndUserSet = new HashSet<EndUser>();
        EndUser EndUser = new Administrator();
        EndUser EndUser2 = new Administrator();
        
        EndUser.setUsername(USERNAME1);
        EndUser2.setUsername(USERNAME2);
        ((HashSet<EndUser>) EndUserSet).add(EndUser);
        ((HashSet<EndUser>) EndUserSet).add(EndUser2);
        
        lenient().when(userDao.findAll()).thenReturn(EndUserSet);

		List<ca.mcgill.ecse321.autorepairsystem.model.EndUser> allEndUsers = null;
		try {
			allEndUsers = service.getAllEndUsers();
		} catch(Exception e) {
			
		}
		
		List<String> usernames = new ArrayList<String>();
		usernames.add(allEndUsers.get(0).getUsername());
		usernames.add(allEndUsers.get(1).getUsername());
		
		assertTrue(usernames.contains(USERNAME1));
		assertTrue(usernames.contains(USERNAME2));
	}
	
	
}
