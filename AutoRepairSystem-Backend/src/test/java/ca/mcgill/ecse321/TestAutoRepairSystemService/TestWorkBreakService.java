package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.lenient;

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
import ca.mcgill.ecse321.autorepairsystem.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestWorkBreakService {
	
	
	@Mock
	private WorkBreakRepository workBreakDao;
	@Mock
	private TechnicianHourRepository technicianHourDao;
	@Mock
	private WorkHourRepository workHourDao;
	@InjectMocks
	private AutoRepairSystemService service;
	
	private static final Integer WORKHOURID=20;
	private static final Time WORKHOURSTARTTIME =Time.valueOf("1:00:00");
	private static final Time WORKHOURENDTIME =Time.valueOf("10:00:00");

	
	private static final Integer ID=30; 
	private static final Time STARTTIME=Time.valueOf("2:00:00");
	private static final Time ENDTIME=Time.valueOf("3:00:00");
	
	private static final Integer ID2=40; 
	private static final Time STARTTIME2=Time.valueOf("4:00:00");
	private static final Time ENDTIME2=Time.valueOf("5:00:00");
	
	private static Boolean WORKBREAKDELETE = false;
		
    @BeforeEach
    public void setMockOutput() {
        lenient().when(workBreakDao.findWorkBreakById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ID)) {
            	WorkBreak workBreak = new WorkBreak();
            	workBreak.setStartBreak(STARTTIME);
            	workBreak.setEndBreak(ENDTIME);
            	workBreak.setId(ID);
            	return workBreak;
            }
            	else {
            		return null;
            	}
        });
        
    	lenient().when(workHourDao.findWorkHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(WORKHOURID)) {
            	TechnicianHour technicianHour = new TechnicianHour();
            	technicianHour.setId(WORKHOURID);
             	technicianHour.setStartTime(WORKHOURSTARTTIME);
             	technicianHour.setEndTime(WORKHOURENDTIME);
            	
            	WorkBreak workBreak = new WorkBreak();
            	workBreak.setStartBreak(STARTTIME);
            	workBreak.setEndBreak(ENDTIME);
            	workBreak.setId(ID);
            	
            	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
            	workBreakSet.add(workBreak);
            	
            	technicianHour.setWorkBreak(workBreakSet);
            	return technicianHour;
            }
            	else {
            		return null;
            	}
        });
    	
    	lenient().when(workHourDao.findWorkHourByWorkBreak(any(WorkBreak.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(((WorkBreak) invocation.getArgument(0)).getId().equals(ID)) {
            	TechnicianHour technicianHour = new TechnicianHour();
            	technicianHour.setId(WORKHOURID);
             	technicianHour.setStartTime(WORKHOURSTARTTIME);
             	technicianHour.setEndTime(WORKHOURENDTIME);
            	
            	WorkBreak workBreak = new WorkBreak();
            	workBreak.setStartBreak(STARTTIME);
            	workBreak.setEndBreak(ENDTIME);
            	workBreak.setId(ID);
            	
            	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
            	workBreakSet.add(workBreak);
            	
            	technicianHour.setWorkBreak(workBreakSet);
            	return technicianHour;
            }
            	else {
            		return null;
            	}
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
    	lenient().doAnswer(invocation -> {
            if(((WorkBreak) invocation.getArgument(0)).getId().equals(ID)) {
            	WORKBREAKDELETE = true;
            }
            return null;
        }).when(workBreakDao).delete(any(WorkBreak.class));
        
        lenient().when(workBreakDao.save(any(WorkBreak.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(workHourDao.save(any(WorkHour.class))).thenAnswer(returnParameterAsAnswer);
    	
    }
    
    @BeforeEach
    public void resetDelete() {
    	WORKBREAKDELETE = false;
    }
    
    @Test
    public void testGetWorkBreak() {
    	WorkBreak workBreak = null;
    	try {
    		workBreak = service.getWorkBreak(ID);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(ID,workBreak.getId());
    }
    
    @Test
    public void testGetWorkBreakFail() {
    	WorkBreak workBreak = null;
    	String error = null;
    	try {
    		workBreak = service.getWorkBreak(ID2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(workBreak);
    	assertEquals(error,"The provided work Break Id is not associated with a work break");
    }
    
    
    @Test
    public void updateWorkBreakFail2() {
    	WorkBreak workBreak = null;
    	String error = null;
    	try {
    		workBreak = service.updateWorkBreak(ID2, STARTTIME2, ENDTIME2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(workBreak);
    	assertEquals(error,"Specified work break doesn't exist!");
    }
    
    @Test
    public void testCreateWorkBreak() {
         
    	WorkBreak workBreak = new WorkBreak();
    	try {
    		workBreak=service.createWorkBreak(WORKHOURID, STARTTIME2, ENDTIME2);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(STARTTIME2, workBreak.getStartBreak());
    }
    
    @Test
    public void testCreateWorkBreakFail() {
         
    	WorkBreak workBreak = null;
    	String error = null;
    	try {
    		workBreak=service.createWorkBreak(null, STARTTIME2, ENDTIME2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(workBreak);
    	assertEquals(error,"A valid work hour ID must be provided!");
    }
    
    @Test
    public void testCreateWorkBreakFail2() {
         
    	WorkBreak workBreak = null;
    	String error = null;
    	try {
    		workBreak=service.createWorkBreak(WORKHOURID, STARTTIME2, ENDTIME2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(workBreak);
    	assertEquals(error,"A valid work hour ID must be provided!");
    }
    
    @Test
    public void testUpdateWorkBreak() {

    	WorkBreak workBreak = new WorkBreak();
    	try {
    		workBreak = service.updateWorkBreak(ID, STARTTIME2, ENDTIME2);
    	} catch(Exception e) {
    			System.out.println(e.getMessage());
    	}
  
    	assertEquals(STARTTIME2, workBreak.getStartBreak());
    	assertEquals(ENDTIME2, workBreak.getEndBreak());
    	
    }
    
    @Test
    public void testDeleteWorkBreak() {
    	
    	WorkBreak workBreak = new WorkBreak();
    	try {
    		workBreak = service.DeleteWorkBreak(ID);
    	} catch(Exception e) {
    			
    	}
    	assertTrue(WORKBREAKDELETE);
    }
    
    @Test
    public void testDeleteWorkBreakFail() {
    	WorkBreak workBreak =null;
    	String error = null;
    	try {
    		workBreak = service.DeleteWorkBreak(ID2);
    	} catch(Exception e) {
    		error = e.getMessage();
    			
    	}
    	assertNull(workBreak);
    	assertEquals(error, "Specified Work Break doesn't exist!");
    	
    }
    

}
