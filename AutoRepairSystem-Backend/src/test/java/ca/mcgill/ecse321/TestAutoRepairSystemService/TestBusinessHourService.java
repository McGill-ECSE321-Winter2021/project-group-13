package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.lenient;

import java.sql.Date;
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
public class TestBusinessHourService {
	
	
	private static final Integer id=20; 
	private static final Time starttime=Time.valueOf("14:59:59");
	private static final Time endtime=Time.valueOf("15:59:59");
	private static final Date date=Date.valueOf("2021-01-02");
	
	private static final Integer id2=30; 
	private static final Time starttime2=Time.valueOf("10:59:59");
	private static final Time endtime2=Time.valueOf("13:59:59");
	private static final Date date2=Date.valueOf("2021-02-02");
	
	
	
	
	@Mock
	private BusinessHourRepository businessHourDao;
	
	
	@InjectMocks
	private AutoRepairSystemService service;
	
    @BeforeEach
    public void setMockOutput() {
        lenient().when(businessHourDao.findBusinessHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(id)) {
            	BusinessHour businesshour= new BusinessHour();
            	businesshour.setId(id);
            	businesshour.setDate(date);
            	businesshour.setEndTime(endtime);
            	businesshour.setStartTime(starttime);
            	businesshour.setWorkBreak(null);
            	
            	return businesshour;
            }
            	else {
            		return null;
            	}
            	
           
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(businessHourDao.save(any(BusinessHour.class))).thenAnswer(returnParameterAsAnswer);
	
    }
	
	
    @Test
    public void createBusinessHour() {
    	BusinessHour businesshour =null;
    	
    	try {
    		businesshour = service.createBusinessHour(starttime, endtime, date);
    	} catch(Exception e) {
    			
    	}
    	assertEquals(starttime, businesshour.getStartTime());
    	assertEquals(endtime, businesshour.getEndTime());
    	assertEquals(date, businesshour.getDate());
    	
    }
    
    @Test
    public void createBusinessHourFail() {
    	BusinessHour businesshour =null;
    	String error = null;
    	
    	try {
    		businesshour = service.createBusinessHour(null, endtime, date);
    	} catch(Exception e) {
    		error = e.getMessage();
    			
    	}
    	assertNull(businesshour);
    	assertEquals(error, "A valid start time must be provided! (non-empty or before end time)");
    	
    	
    }
    
    @Test
    public void testGetBusinessHour() {
    	BusinessHour businesshour =  null;
    	try {
    		businesshour = service.getBusinessHour(id);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(id,businesshour.getId());
    }
    
    @Test
    public void testGetBusinessHourFail() {
    	BusinessHour businesshour =  null;
    	String error = null;
    	try {
    		businesshour = service.getBusinessHour(id2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(businesshour);
    	assertEquals(error,"business hour cannot be found!");
    }
	
    
    @Test
    public void testUpdateBusinessHour() {
    	BusinessHour businesshour =  null;
    	try {
    		businesshour = service.updateBusinessHour(id, starttime, endtime, date);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(id,businesshour.getId());
    	assertEquals(starttime, businesshour.getStartTime());
    	assertEquals(endtime, businesshour.getEndTime());
    	assertEquals(date, businesshour.getDate());
    }
    
    @Test
    public void testUpdateBusinessHourFail() {
    	BusinessHour businesshour =  null;
    	String error = null;
    	try {
    		businesshour = service.updateBusinessHour(id, endtime, starttime, date);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(businesshour);
    	assertEquals(error,"A valid start time must be provided! (non-empty or before end time)");
    }
    
    @Test
    public void testUpdateBusinessHourWorkBreak() {
    	WorkBreak workBreak = new WorkBreak();
     	workBreak.setStartBreak(starttime);
     	workBreak.setEndBreak(endtime);
     	workBreak.setId(id);
     	
     	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
     	workBreakSet.add(workBreak);
    	BusinessHour businesshour =  null;
    	try {
    		businesshour = service.updateBusinessHourWorkBreak(id, workBreakSet);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(id,businesshour.getId());
  
    }
    
    @Test
    public void testUpdateBusinessHourWorkBreakFail() {
    	String error = null;
    	WorkBreak workBreak = new WorkBreak();
     	workBreak.setStartBreak(starttime);
     	workBreak.setEndBreak(endtime);
     	workBreak.setId(id);
     	
     	Set<WorkBreak> workBreakSet = new HashSet<WorkBreak>();
     	workBreakSet.add(workBreak);
    	BusinessHour businesshour =  null;
    	try {
    		businesshour = service.updateBusinessHourWorkBreak(id2, workBreakSet);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertEquals(error,"Business Hour cannot be found");
  
    }
    
    @Test
    public void deleteBusinessHourFail() {
    	BusinessHour businesshour =  null;
    	String error = null;
    	try {
    		businesshour = service.deleteBusinessHour(id2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertEquals(error,"Specified business hour doesn't exist!");
    }
    
    @Test
    public void getAllBusinessHours() {
    	Set <BusinessHour> businessHourset = new HashSet<BusinessHour>();
    	
    	
    	BusinessHour testbh1 = new BusinessHour();
        BusinessHour testbh2 = new BusinessHour();
        testbh1.setDate(date);
        testbh1.setStartTime(starttime);
        testbh1.setEndTime(endtime);
        testbh1.setId(id);
        testbh1.setWorkBreak(null);
        
        testbh2.setDate(date2);
        testbh2.setEndTime(endtime2);
        testbh2.setStartTime(starttime2);
        testbh2.setId(id2);
        testbh2.setWorkBreak(null);
        
     
        businessHourset.add(testbh1);
        businessHourset.add(testbh2);
        
        lenient().when(businessHourDao.findAll()).thenReturn(businessHourset);

		List<BusinessHour> allbhs = null;
		try {
			allbhs = service.getAllBusinessHours();
		} catch(Exception e) {
			
		}
		
		List<Date> dates = new ArrayList<Date>();
		dates.add(allbhs.get(0).getDate());
		dates.add(allbhs.get(1).getDate());
		
		assertTrue(dates.contains(date));
		assertTrue(dates.contains(date2));
		
 		
 	}
    
    
    }
    
    
    
    
    
