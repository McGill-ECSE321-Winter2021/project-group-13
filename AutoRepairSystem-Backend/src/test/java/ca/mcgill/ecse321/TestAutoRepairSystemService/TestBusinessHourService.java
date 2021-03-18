package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

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
	
	
	private static final Integer ID=10; 
	private static final Time STARTTIME=Time.valueOf("14:59:59");
	private static final Time ENDTIME=Time.valueOf("15:59:59");
	private static final Date DATE=Date.valueOf("2021-01-02");
	
	private static final Integer ID2=20; 
	private static final Time STARTTIME2=Time.valueOf("10:59:59");
	private static final Time ENDTIME2=Time.valueOf("13:59:59");
	private static final Date DATE2=Date.valueOf("2021-02-02");

	private static final Integer ID3=30; 
	private static final Time STARTTIME3=Time.valueOf("10:00:00");
	private static final Time ENDTIME3=Time.valueOf("12:00:00");
	private static final Date DATE3=Date.valueOf("2021-02-02");
	
	
	private static final Integer TECHHOURID = 40;
	private static final Time TECHHOURSTARTTIME = Time.valueOf("14:59:59");
	private static final Time TECHHOURENDTIME = Time.valueOf("15:20:00");
	
	private static final Integer APTID = 50;
	private static final Time APTSTARTTIME = Time.valueOf("15:10:00");
	private static final Time APTENDTIME = Time.valueOf("15:59:59");
	
	private static Boolean BUSINESSHOURDELETE = false;
	private static Boolean BUSINESSHOURDELETE3 = false;
	private static Boolean TECHHOURDELETE = false;
	private static Boolean APTDELETE = false;
	
	
	@Mock
	private BusinessHourRepository businessHourDao;
	
	@Mock
	private TechnicianHourRepository technicianHourDao;
	
	@Mock
	private AppointmentRepository appointmentDao;
	
	@InjectMocks
	private AutoRepairSystemService service;
	
    @BeforeEach
    public void setMockOutput() {
        lenient().when(businessHourDao.findBusinessHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ID)) {
            	BusinessHour businessHour= new BusinessHour();
            	businessHour.setId(ID);
            	businessHour.setDate(DATE);
            	businessHour.setEndTime(ENDTIME);
            	businessHour.setStartTime(STARTTIME);
   
            	return businessHour;
            } else if(invocation.getArgument(0).equals(ID3)) {
            	BusinessHour businessHour= new BusinessHour();
            	businessHour.setId(ID3);
            	businessHour.setDate(DATE3);
            	businessHour.setEndTime(ENDTIME3);
            	businessHour.setStartTime(STARTTIME3);
            	return businessHour;
            } else {
        		return null;
        	}
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(businessHourDao.save(any(BusinessHour.class))).thenAnswer(returnParameterAsAnswer);
        
        // Set Mock Outputs for deleting business hour
        BUSINESSHOURDELETE = false;
    	lenient().doAnswer(invocation -> {
            if(((BusinessHour) invocation.getArgument(0)).getId().equals(ID)) {
            	BUSINESSHOURDELETE = true;
            } else if (((BusinessHour) invocation.getArgument(0)).getId().equals(ID3)) {
            	BUSINESSHOURDELETE3 = true;
            }
            return null;
        }).when(businessHourDao).delete(any(BusinessHour.class));
        
    	lenient().doAnswer(invocation -> {
            if(((TechnicianHour) invocation.getArgument(0)).getId().equals(TECHHOURID)) {
            	TECHHOURDELETE = true;
            }
            return null;
        }).when(technicianHourDao).delete(any(TechnicianHour.class));
    	
    	lenient().doAnswer(invocation -> {
            if(((Appointment) invocation.getArgument(0)).getId().equals(APTID)) {
            	APTDELETE = true;
            }
            return null;
        }).when(appointmentDao).delete(any(Appointment.class));
    	
        lenient().when(technicianHourDao.findTechnicianHourByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
        	Set<TechnicianHour> technicianHourSet = new HashSet<TechnicianHour>();
        	if(invocation.getArgument(0).equals(DATE)) {
            	TechnicianHour technicianHour = new TechnicianHour();
            	technicianHour.setId(TECHHOURID);
            	technicianHour.setStartTime(TECHHOURSTARTTIME);
            	technicianHour.setEndTime(TECHHOURENDTIME);
            	technicianHour.setDate(DATE);
            	technicianHourSet.add(technicianHour);
            	return technicianHourSet;
        	} else {
    			return technicianHourSet;
        	}
        });
        
        lenient().when(appointmentDao.findAppointmentByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
        	Set<Appointment> appointmentSet = new HashSet<Appointment>();
        	if(invocation.getArgument(0).equals(DATE)) {
            	Appointment appointment = new Appointment();
            	appointment.setId(APTID);
            	appointment.setStartTime(APTSTARTTIME);
            	appointment.setEndTime(APTENDTIME);
            	appointment.setDate(DATE);
            	appointmentSet.add(appointment);
            	return appointmentSet;
        	} else {
    			return appointmentSet;
        	}
        });
    	
        
    }
    
    @BeforeEach
    public void resetDelete() {
    	BUSINESSHOURDELETE = false;
    	BUSINESSHOURDELETE3 = false;
    	TECHHOURDELETE = false;
    	APTDELETE = false;
    }
	
	
    @Test
    public void createBusinessHour() {
    	BusinessHour businesshour =null;
    	
    	try {
    		businesshour = service.createBusinessHour(STARTTIME, ENDTIME, DATE);
    	} catch(Exception e) {
    			
    	}
    	assertEquals(STARTTIME, businesshour.getStartTime());
    	assertEquals(ENDTIME, businesshour.getEndTime());
    	assertEquals(DATE, businesshour.getDate());
    	
    }
    
    @Test
    public void createBusinessHourFail() {
    	BusinessHour businesshour =null;
    	String error = null;
    	
    	try {
    		businesshour = service.createBusinessHour(null, ENDTIME, DATE);
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
    		businesshour = service.getBusinessHour(ID);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(ID,businesshour.getId());
    }
    
    @Test
    public void testGetBusinessHourFail() {
    	BusinessHour businesshour =  null;
    	String error = null;
    	try {
    		businesshour = service.getBusinessHour(ID2);
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
    		businesshour = service.updateBusinessHour(ID, STARTTIME, ENDTIME, DATE);
    	} catch(Exception e) {
    		
    	}
    	assertEquals(ID,businesshour.getId());
    	assertEquals(STARTTIME, businesshour.getStartTime());
    	assertEquals(ENDTIME, businesshour.getEndTime());
    	assertEquals(DATE, businesshour.getDate());
    }
    
    @Test
    public void testUpdateBusinessHourFail() {
    	BusinessHour businesshour =  null;
    	String error = null;
    	try {
    		businesshour = service.updateBusinessHour(ID, ENDTIME, STARTTIME, DATE);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertNull(businesshour);
    	assertEquals(error,"A valid start time must be provided! (non-empty or before end time)");
    }
    
    @Test
    public void testDeleteBusinessHour() {
  
    	try {
    		service.deleteBusinessHour(ID);
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	assertTrue(BUSINESSHOURDELETE);
    	assertTrue(TECHHOURDELETE);
    	assertTrue(APTDELETE);
    }
    
    @Test
    public void testDeleteBusinessHour2() {
  
    	try {
    		service.deleteBusinessHour(ID3);
    	} catch(Exception e) {
    		
    	}
    	assertTrue(BUSINESSHOURDELETE3);
    	assertFalse(TECHHOURDELETE);
    	assertFalse(APTDELETE);
    }
    
    @Test
    public void testDeleteBusinessHourFail() {
    	String error = null;
    	try {
    		service.deleteBusinessHour(ID2);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertEquals(error,"Specified business hour doesn't exist!");
    }
    
    @Test
    public void deleteBusinessHourFail2() {
    	String error = null;
    	try {
    		service.deleteBusinessHour(null);
    	} catch(Exception e) {
    		error = e.getMessage();
    		
    	}
    	assertEquals(error,"A valid business hour ID must be provided!");
    }
    
    @Test
    public void getAllBusinessHours() {
    	Set <BusinessHour> businessHourset = new HashSet<BusinessHour>();
    	
    	
    	BusinessHour testbh1 = new BusinessHour();
        BusinessHour testbh2 = new BusinessHour();
        testbh1.setDate(DATE);
        testbh1.setStartTime(STARTTIME);
        testbh1.setEndTime(ENDTIME);
        testbh1.setId(ID);
        testbh1.setWorkBreak(null);
        
        testbh2.setDate(DATE2);
        testbh2.setEndTime(ENDTIME2);
        testbh2.setStartTime(STARTTIME2);
        testbh2.setId(ID2);
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
		
		assertTrue(dates.contains(DATE));
		assertTrue(dates.contains(DATE2));
 	}
    
    
    }
    
    
    
    
    
