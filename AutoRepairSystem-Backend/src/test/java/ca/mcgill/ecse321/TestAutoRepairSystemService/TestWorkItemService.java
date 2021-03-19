package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import ca.mcgill.ecse321.autorepairsystem.model.WorkItem;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
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
	
	private static final String NAME3 = "TestWorkItem3";
	private static final int DURATION3 = 13;
	private static final int PRICE3 = 23;
	
	private static final Integer ID = 99;
	private static final Time APTSTARTTIME =Time.valueOf("1:00:00");
	private static final Time APTENDTIME =Time.valueOf("10:00:00");
	private static final Date APTDATE = Date.valueOf("2020-01-01");
	

	
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
            } else if(invocation.getArgument(0).equals(NAME3)) {
            	WorkItem workItem = new WorkItem();
            	workItem.setName(NAME3);
            	workItem.setDuration(DURATION3);
            	workItem.setPrice(PRICE3);
            	return workItem;
            }
            	else {
            		return null;
            	}
        });
        
        lenient().when(appointmentDao.findAppointmentByWorkItem(any(WorkItem.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(((WorkItem) invocation.getArgument(0)).getName().equals(NAME3)) {
            	Appointment appointment = new Appointment();
            	appointment.setId(ID);
            	appointment.setStartTime(APTSTARTTIME);
            	appointment.setEndTime(APTENDTIME);
            	appointment.setDate(APTDATE);

            	Set<Appointment> appointmentSet = new HashSet<Appointment>();
            	appointmentSet.add(appointment);
            	
            	return appointmentSet;
            }
            	else {
            		return new HashSet<Appointment>();
            	}
        });
        
        lenient().doAnswer(invocation -> {
            if(((WorkItem) invocation.getArgument(0)).getName().equals(NAME)) {
            	WORKITEMDELETE = true;
            }
            return null;
        }).when(workItemDao).delete(any(WorkItem.class));
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(workItemDao.save(any(WorkItem.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@BeforeEach
	public void resetDelete() {
		WORKITEMDELETE = false;
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
		try {
			service.deleteWorkItem(NAME);
		} catch(Exception e) {
			e.getMessage();
			
		}
		assertTrue(WORKITEMDELETE);
	}
	
	@Test
	public void deleteWorkItemFail() {
		String error = null;
		try {
			service.deleteWorkItem(null);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertFalse(WORKITEMDELETE);
		assertEquals(error,"A valid name must be provided");
	}
	
	@Test
	public void deleteWorkItemFail2() {

		String error = null;
		try {
			service.deleteWorkItem(NAME2);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertFalse(WORKITEMDELETE);
		assertEquals(error,"Work item cannot be found.");
	}
	
	@Test
	public void deleteWorkItemFail3() {

		String error = null;
		try {
			service.deleteWorkItem(NAME3);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertFalse(WORKITEMDELETE);
		assertEquals(error,"There are appointments associated with this work item; it cannot be deleted!");
	}
	
	
	
	
	
	
	
}