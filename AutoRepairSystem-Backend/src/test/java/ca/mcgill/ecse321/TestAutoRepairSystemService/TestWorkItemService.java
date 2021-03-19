package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
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
import ca.mcgill.ecse321.autorepairsystem.dao.WorkItemRepository;
import ca.mcgill.ecse321.autorepairsystem.dao.AppointmentRepository;

@ExtendWith(MockitoExtension.class)
public class TestWorkItemService {
	
	
	@Mock
	private WorkItemRepository workItemDao;
	@Mock
	private AppointmentRepository appointmentDao;
	@InjectMocks
	private AutoRepairSystemService service;
	
	private static final String NAME = "TestWorkItem";
	private static final int DURATION = 10;
	private static final int PRICE = 20;
	
	private static final String NAME2 = "TestWorkItem2";
	private static final int DURATION2 = 11;
	private static final int PRICE2 = 22;
	
	private static final Integer ID = 99;
	private static final Time APTSTARTTIME =Time.valueOf("1:00:00");
	private static final Time APTENDTIME =Time.valueOf("10:00:00");
	
	private static final int INVALIDDURATION = -1;
	private static final int INVALIDPRICE = -1;
	
	

	private static Boolean WORKITEMDELETE = false;
	
	@BeforeEach
    public void setMockOutput() {
        lenient().when(workItemDao.findWorkItemByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(NAME)) {
            	WorkItem workItem = new WorkItem();
            	workItem.setName(NAME);
            	workItem.setDuration(DURATION);
            	workItem.setPrice(PRICE);
            	return workItem;
            }
            	else {
            		return null;
            	}
        });
	}
	
	@Test
	public void testCreateWorkItem() {
		WorkItem workItem = null;
		try {
			workItem = service.createWorkItem(NAME2, DURATION2, PRICE2);
		} catch(Exception e) {
			
		}
		assertEquals(NAME2,workItem.getName());
		assertEquals(DURATION2,workItem.getDuration());
		assertEquals(PRICE2,workItem.getPrice());
	}
	
	@Test
	public void testCreateWorkItemFail() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.createWorkItem(null, DURATION2, PRICE2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"A valid service name must be provide!");
	}
	
	@Test
	public void testCreateWorkItemFail2() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.createWorkItem(NAME, DURATION2, PRICE2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"WorkItem with name: " + NAME + " already exists");
	}
	
	@Test
	public void testCreateWorkItemFail3() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.createWorkItem(NAME2, INVALIDDURATION, PRICE2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"Duration cannot be less than zero!");
	}
	
	@Test
	public void testCreateWorkItemFail4() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.createWorkItem(NAME2, DURATION2, INVALIDPRICE);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"Price cannot be less than zero!");
	}
	
	@Test
	public void testGetWorkItem() {
		WorkItem workItem = null;
		try {
			workItem = service.getWorkItem(NAME);
		} catch(Exception e) {
		}
		assertEquals(NAME,workItem.getName());
		assertEquals(DURATION,workItem.getDuration());
		assertEquals(PRICE,workItem.getPrice());
	}
	
	@Test
	public void testGetWorkItemFail() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.getWorkItem(NAME2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"Work item cannot be found!");
	}
	
	@Test
	public void updateWorkItem() {
		WorkItem workItem = null;
		try {
			workItem = service.updateWorkItem(NAME,DURATION2,PRICE2);
		} catch(Exception e) {
		}
		assertEquals(NAME,workItem.getName());
		assertEquals(DURATION2,workItem.getDuration());
		assertEquals(PRICE2,workItem.getPrice());
	}
	
	@Test
	public void updateWorkItemFail() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.updateWorkItem(null,DURATION2,PRICE2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"A valid service name must be provide!");
	}
	
	@Test
	public void updateWorkItemFail2() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.updateWorkItem(NAME2,DURATION2,PRICE2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"WorkItem with name does not exist!");
	}
	
	@Test
	public void updateWorkItemFail3() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.updateWorkItem(NAME,INVALIDDURATION,PRICE2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"Duration cannot be less than zero!");
	}
	
	@Test
	public void updateWorkItemFail4() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.updateWorkItem(NAME,DURATION,INVALIDPRICE);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"Price cannot be less than zero!");
	}
	
	@Test
	public void deleteWorkItem() {
		WorkItem workItem = null;
		try {
			workItem = service.deleteWorkItem(NAME)
		} catch(Exception e) {
		}
		AssertTrue(WORKITEMDELETE);
	}
	
	@Test
	public void deleteWorkItemFail() {
		WorkItem workItem = null;
		String error = null;
		try {
			workItem = service.updateWorkItem(NAME,DURATION,INVALIDPRICE);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(workItem);
		assertEquals(error,"Price cannot be less than zero!");
	}
	
	
	
	
	
	
	
}