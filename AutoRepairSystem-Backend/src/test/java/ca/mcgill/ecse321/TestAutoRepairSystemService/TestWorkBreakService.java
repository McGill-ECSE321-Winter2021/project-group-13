package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.lenient;

import java.sql.Time;
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
public class TestWorkBreakService {
	
	
	@Mock
	private WorkBreakRepository workBreakDao;
	@Mock
	private TechnicianHourRepository technicianHourDao;
	@Mock
	private WorkHourRepository WorkHourDao;
	
	private static final Integer id=20; 
	private static final Time time=Time.valueOf("14:59:59");
	private static final Time time2=Time.valueOf("15:59:59");
	private static final Integer WORKHOURID=20;
	
	private static final Integer id2=30; 
	private static final Time STARTTIME2=Time.valueOf("13:59:59");
	private static final Time ENDTIME2=Time.valueOf("14:30:59");
	
	@InjectMocks
	private AutoRepairSystemService service;
	
    @BeforeEach
    public void setMockOutput() {
        lenient().when(workBreakDao.findWorkBreakById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(id)) {
            	WorkBreak workbreak = new WorkBreak();
            	workbreak.setStartBreak(time);
            	workbreak.setEndBreak(time2);
            	workbreak.setId(id);
            	return workbreak;
            }
            	else {
            		return null;
            	}
            	
           
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(workBreakDao.save(any(WorkBreak.class))).thenAnswer(returnParameterAsAnswer);
	
    }
    
    @Test
    public void testGetWorkBreakById() {
    	WorkBreak workbreak = null;
    	try {
    		workbreak = service.getWorkBreak(id);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(id,workbreak.getId());
    }
    
    @Test
    public void testGetWorkBreakByIdFail() {
    	WorkBreak workbreak = null;
    	String error = null;
    	try {
    		workbreak = service.getWorkBreak(id2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(workbreak);
    	assertEquals(error,"The provided work Break Id is not associated with a work break");
    }
    
    
    @Test
    public void updateWorkBreakDoesntExist() {
    	WorkBreak workbreak = null;
    	String error = null;
    	try {
    		workbreak = service.updateWorkBreak(id2, time, time2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(workbreak);
    	assertEquals(error,"Specified work break doesn't exist!");
    }
    
    @Test
    public void deleteWorkBreakDoesntExist() {
    	WorkBreak workbreak =null;
    	String error = null;
    	try {
    		workbreak = service.DeleteWorkBreak(id2);
    	} catch(Exception e) {
    		error = e.getMessage();
    			
    	}
    	assertEquals(error, "Specified Work Break doesn't exist!");
    	
    }
    
    
    @Test
    public void testCreateWorkBreak() {
    	
    	 lenient().when(WorkHourDao.findWorkHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
             if(invocation.getArgument(0).equals(WORKHOURID)) {
             	TechnicianHour technicianHour = new TechnicianHour();
             	technicianHour.setId(WORKHOURID);
             	
             	WorkBreak workBreak = new WorkBreak();
             	workBreak.setStartBreak(time);
             	workBreak.setEndBreak(time2);
             	workBreak.setId(id);
             	
             	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
             	workBreakSet.add(workBreak);
             	
             	technicianHour.setWorkBreak(workBreakSet);
             	return technicianHour;
             }
             	else {
             		return null;
             	}
             	
            
         });
         
    	WorkBreak workbreak = new WorkBreak();
    	String error = null;
    	try {
    		workbreak=service.createWorkBreak(WORKHOURID, STARTTIME2, ENDTIME2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	
    	assertEquals(STARTTIME2, workbreak.getStartBreak());
    	
    }
    
    @Test
    public void updateWorkBreak() {
    	
    	lenient().when(WorkHourDao.findWorkHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(WORKHOURID)) {
            	TechnicianHour technicianHour = new TechnicianHour();
            	technicianHour.setId(WORKHOURID);
            	
            	WorkBreak workBreak = new WorkBreak();
            	workBreak.setStartBreak(time);
            	workBreak.setEndBreak(time2);
            	workBreak.setId(id);
            	
            	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
            	workBreakSet.add(workBreak);
            	
            	technicianHour.setWorkBreak(workBreakSet);
            	return technicianHour;
            }
            	else {
            		return null;
            	}
            	
           
        });
    	
    	WorkBreak workbreak = new WorkBreak();
    	workbreak.setStartBreak(time);
    	workbreak.setEndBreak(time2);
    
    	try {
    		workbreak = service.updateWorkBreak(WORKHOURID, time, time2);
    	} catch(Exception e) {
    			
    	}
  
    	assertEquals(time, workbreak.getStartBreak());
    	assertEquals(time2, workbreak.getEndBreak());
    	
    }
    
    @Test
    public void deleteWorkBreak() {
    	lenient().when(WorkHourDao.findWorkHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(WORKHOURID)) {
            	TechnicianHour technicianHour = new TechnicianHour();
            	technicianHour.setId(WORKHOURID);
            	
            	WorkBreak workBreak = new WorkBreak();
            	workBreak.setStartBreak(time);
            	workBreak.setEndBreak(time2);
            	workBreak.setId(id);
            	
            	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
            	workBreakSet.add(workBreak);
            	
            	technicianHour.setWorkBreak(workBreakSet);
            	return technicianHour;
            }
            	else {
            		return null;
            	}
            	
           
        });
    	
    	WorkBreak workbreak = new WorkBreak();
    	try {
    		workbreak = service.DeleteWorkBreak(id);
    	} catch(Exception e) {
    			
    	}
    	assertNull(workbreak);
    	
    }

}
