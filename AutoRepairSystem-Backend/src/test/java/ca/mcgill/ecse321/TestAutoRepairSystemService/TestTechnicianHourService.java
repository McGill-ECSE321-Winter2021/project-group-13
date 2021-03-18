package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autorepairsystem.dao.TechnicianHourRepository;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;

@ExtendWith(MockitoExtension.class)
public class TestTechnicianHourService {
	
	private static final Integer id=20; 
	private static final Technician technician1 = new Technician();
	private static final Time starttime=Time.valueOf("14:59:59");
	private static final Time endtime=Time.valueOf("15:59:59");
	private static final Date date=Date.valueOf("2021-01-02");
	
	private static final Integer id2=30; 
	private static final Time starttime2=Time.valueOf("10:59:59");
	private static final Time endtime2=Time.valueOf("13:59:59");
	private static final Date date2=Date.valueOf("2021-02-02");
	
	@Mock
	private TechnicianHourRepository technicianHourDao;
	
	@InjectMocks
	private AutoRepairSystemService service;
	
    @BeforeEach
    public void setMockOutput() {
        lenient().when(technicianHourDao.findTechnicianHourById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(id)) {
            	TechnicianHour technicianHour = new TechnicianHour();
            	technicianHour.setId(id);
            	technicianHour.setDate(date);
            	technicianHour.setEndTime(endtime);
            	technicianHour.setStartTime(starttime);
            	technicianHour.setWorkBreak(null);
            	
            	return technicianHour;
            }
            	else {
            		return null;
            	}
            	
           
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        	return invocation.getArgument(0);
        };
        
        lenient().when(technicianHourDao.save(any(TechnicianHour.class))).thenAnswer(returnParameterAsAnswer);
	
    }
    
    @Test
    public void createTechnicianHour() {
    	TechnicianHour technicianHour = null;
    	
    	try {
    		technicianHour = service.createTechnicianHour(starttime, endtime, date);
    	} catch(Exception e) {
    			
    	}
    	assertEquals(starttime, technicianHour.getStartTime());
    	assertEquals(endtime, technicianHour.getEndTime());
    	assertEquals(date, technicianHour.getDate());
    	
    }

}
