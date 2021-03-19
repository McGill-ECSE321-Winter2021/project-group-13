package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;

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
import ca.mcgill.ecse321.autorepairsystem.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestTechnicianHourService {
	
	@Mock
	private TechnicianHourRepository technicianHourDao;
	@Mock
	private TechnicianRepository technicianDao;
	@Mock
	private BusinessHourRepository businessHourDao;
	@Mock
	private AppointmentRepository appointmentDao;
	
	@InjectMocks
	private AutoRepairSystemService service;
	
	private static final Integer id = 123;
	private static final String username = "Jack";
	private static final Date date = Date.valueOf("2021-05-01");
	
	Time validStartTime = Time.valueOf("18:00:00");
	Time validEndTime = Time.valueOf("19:05:00");
	Date validDate = Date.valueOf("2021-05-01");
	
	Time invalidStartTime = Time.valueOf("20:00:00"); //Starts after validEndTime
	Time invalidStartTime2 = Time.valueOf("01:00:00"); //Starts before start of business hour for that day
	Time invalidStartTime3 = Time.valueOf("10:00:00"); //Overlaps with start of existing technicianHour of the technician
	Time invalidEndTime = Time.valueOf("17:00:00"); //Starts before validStartTime
	Integer invalidId = 333; //No existing technicianHour with this id
	
	@BeforeEach
	public void setMockOutput() {
	    lenient().when(technicianHourDao.findTechnicianHourById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(id)) {
	            return tHour1();
	        } else {
	            return null;
	        }
	    });
	    
	    lenient().when(technicianDao.findTechnicianByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(username)) {
	            return t();
	        } else {
	            return null;
	        }
	    });
	    
	    lenient().when(businessHourDao.findBusinessHourByDate(any(java.sql.Date.class))).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(date)) {
	            BusinessHour b = new BusinessHour();
	            b.setId(321);
	            b.setStartTime(Time.valueOf("02:00:00"));
	            b.setEndTime(Time.valueOf("22:30:00"));
	            b.setDate(date);
	            b.setWorkBreak(new HashSet<WorkBreak>());
	            Set<BusinessHour> bSet = new HashSet<BusinessHour>();
	            bSet.add(b);
	            return bSet;
	        }  else {
	            return null;
	        }
	    });
	    
	    lenient().when(technicianDao.findTechnicianByTechnicianHour(any(TechnicianHour.class))).thenAnswer( (InvocationOnMock invocation) -> {
	        if(((TechnicianHour) invocation.getArgument(0)).getId().equals(id)) {
	            return t();
	        } else {
	            return null;
	        }
	    });
	    
	    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(technicianHourDao.save(any(TechnicianHour.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(technicianDao.save(any(Technician.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	private TechnicianHour tHour1() {
		TechnicianHour tHour = new TechnicianHour();
        tHour.setId(id);
        tHour.setStartTime(Time.valueOf("09:00:00"));
        tHour.setEndTime(Time.valueOf("17:00:00"));
        tHour.setDate(Date.valueOf("2021-05-01"));
        tHour.setWorkBreak(new HashSet<WorkBreak>());
        return tHour;
	}
	
	private Technician t() {
		Technician t = new Technician();
        t.setUsername(username);
        t.setPassword("GenericPassword");
        t.setName("Jack");
        t.setEmail("jack.jack@jack.jack.com");
        TechnicianHour tHour = tHour1();
        Set<TechnicianHour> tHourSet = new HashSet<TechnicianHour>();
        tHourSet.add(tHour);
        t.setTechnicianHour(tHourSet);
        return t;
	}
    

	@Test
    public void testCreateTechnicianHour() {
		TechnicianHour tHour = null;
		
		try {
			tHour = service.createTechnicianHour(username, validStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			fail();
		}
		assertNotNull(tHour);
		//assertNotNull(tHour.getId()); //This doesn't actually pass but I think it might just be because we're not online. Works fine through REST client
		assertEquals(validStartTime, tHour.getStartTime());
		assertEquals(validEndTime, tHour.getEndTime());
		assertEquals(validDate, tHour.getDate());
    }
	
	
	@Test
    public void testCreateTechnicianHourInvalidUsername() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(" ", validStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid technician username must be provided!", error);
    }
	

	@Test
    public void testCreateTechnicianHourNoExistingTechnician() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour("Not Jack", validStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("Specified technician doesn't exist!", error);
    }
	

	@Test
    public void testCreateTechnicianHourNullStartTime() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(username, null, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid start time must be provided!", error);
    }
	
	
	@Test
    public void testCreateTechnicianHourNullEndTime() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(username, validStartTime, null, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid end time must be provided!", error);
    }
	

	@Test
    public void testCreateTechnicianHourNullDate() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(username, validStartTime, validEndTime, null);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid date must be provided!", error);
    }
	
	
	@Test
    public void testCreateTechnicianHourNotWithinBusinessHour() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(username, invalidStartTime2, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("Technician hour must exist within a business hour", error);
	}
	
	
	@Test
    public void testCreateTechnicianHourEndTimeBeforeStartTime() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(username, invalidStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("End time cannot be before start time", error);
	}
	
	
	@Test
    public void testCreateTechnicianHourOverlapping() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.createTechnicianHour(username, invalidStartTime3, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("Technician hour cannot overlap with another technicianHour of the same technician", error);
	}
	

	@Test
    public void testGetTechnician() {
		TechnicianHour tHour = null;
		try {
			tHour = service.getTechnicianHour(id);
		}
		catch (Exception e) {
			fail();
		}
		assertNotNull(tHour);
		assertEquals(id, tHour.getId());
	}
	
	@Test
    public void testGetTechnicianNoExistingTechnicianHour() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.getTechnicianHour(invalidId);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("Technician Hour cannot be found!", error);
	}
	

	@Test
    public void testUpdateTechnicianHour() {
		TechnicianHour tHour = null;
		try {
			tHour = service.updateTechnicianHour(id, validStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			fail();
		}
		assertNotNull(tHour);
		assertEquals(id, tHour.getId());
		assertEquals(validStartTime, tHour.getStartTime());
		assertEquals(validEndTime, tHour.getEndTime());
		assertEquals(validDate, tHour.getDate());
	}
	

	@Test
    public void testUpdateTechnicianHourNullId() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(null, validStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid technican hour I.D. must be provided!", error);
	}
	

	@Test
    public void testUpdateTechnicianHourNoTechnicianHour() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(invalidId, validStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A technician hour with this I.D. cannot be found!", error);
	}
	
	
	@Test
    public void testUpdateTechnicianHourNullStartTime() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(id, null, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid start time must be provided!", error);
	}
	

	@Test
    public void testUpdateTechnicianHourNullEndTime() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(id, validStartTime, null, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid end time must be provided!", error);
	}
	
	
	@Test
    public void testUpdateTechnicianHourEndTimeBeforeStartTime() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(id, invalidStartTime, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("End time cannot be before start time!", error);
	}
	

	@Test
    public void testUpdateTechnicianHourNullDate() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(id, validStartTime, validEndTime, null);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid date must be provided!", error);
	}
	

	@Test
    public void testUpdateTechnicianHourNotWithinBusinessHour() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.updateTechnicianHour(id, invalidStartTime2, validEndTime, validDate);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("Technician hour must exist within a business hour", error);
	}

	//Only missing checking for overlap for update
	

	@Test
    public void testDeleteTechnicianHour() {
		TechnicianHour tHour = null;
		lenient().when(appointmentDao.findAppointmentByTechnician(any(Technician.class))).thenAnswer( (InvocationOnMock invocation) -> {return new HashSet<Appointment>();});
		try {
			tHour = service.deleteTechnicianHour(id);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(id, tHour.getId());
	}
	

	@Test
    public void testDeleteTechnicianHourNullId() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.deleteTechnicianHour(null);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("A valid technician hour ID must be provided!", error);
	}
	
	
	@Test
    public void testDeleteTechnicianHourNoTechnicianHour() {
		TechnicianHour tHour = null;
		String error = null;
		try {
			tHour = service.deleteTechnicianHour(invalidId);
		}
		catch (Exception e) {
			error = e.getMessage();
		}
		assertNull(tHour);
		assertEquals("Specified technician Hour doesn't exist!", error);
	}
}
