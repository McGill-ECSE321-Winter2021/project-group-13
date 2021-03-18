package ca.mcgill.ecse321.TestAutoRepairSystemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.autorepairsystem.model.*;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;
import ca.mcgill.ecse321.autorepairsystem.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestAppointmentService {
  
  @Mock
  AppointmentRepository appointmentDao;
  
  @Mock
  private CustomerRepository customerDao;
  
  @Mock
  private TechnicianHourRepository technicianHourDao;
  
  @Mock
  private TechnicianRepository technicianDao;
  
  @InjectMocks
  private AutoRepairSystemService service;
  
  private static final String USERNAME1 = "testCustomer1";
  private static final String PASSWORD1 = "testPassword1";
  private static final String NAME1 = "testName1";
  private static final String EMAIL1 = "testEmail1";
  
  private static final String USERNAME2 = "testCustomer2";
  private static final String PASSWORD2 = "testPassword2";
  private static final String NAME2 = "testName2";
  private static final String EMAIL2 = "testEmail2";
  
  private static Technician technician1;
  private static Technician technician2;

  //Tests for:
  
  //getAllAppointments()
  //getAppointmentsByCustomer(Customer customer)
  //getAppointmentsByDate(java.sql.Date date)
  //getAppointmentsByTechnician(Technician technician)
  //createAppointment(Set<ca.mcgill.ecse321.autorepairsystem.model.Service> services, Customer customer, Technician technician, java.sql.Time startTime, java.sql.Time endTime, java.sql.Date date)
  //deleteAppointment(Integer id)
  //getTechnicianAvailableTechnicianHoursByDate(java.sql.Date date, int minDurationInMin)
  //updateAppointment(Integer id, Technician technician, Set<WorkItem> services, java.sql.Time startTime, java.sql.Date date)
  
  @BeforeEach
  public void setMockOutput() {
    
    technician1 = new Technician();
    technician1.setUsername(USERNAME2);
    technician1.setPassword(PASSWORD2);
    technician1.setName(NAME2);
    technician1.setEmail(EMAIL2);
    
    technician2 = new Technician();
    technician2.setUsername(USERNAME1);
    technician2.setPassword(PASSWORD1);
    technician2.setName(NAME1);
    technician2.setEmail(EMAIL1);
    
    lenient().when(appointmentDao.findAppointmentById(any(Integer.class))).thenAnswer((InvocationOnMock invocation) -> {

      if (invocation.getArgument(0).equals(1)) {
        
        Customer customer = new Customer();
        customer.setUsername(USERNAME1);
        customer.setPassword(PASSWORD1);
        customer.setName(NAME1);
        customer.setEmail(EMAIL1);
        customer.setAmountOwed(100);
        
        Set<WorkItem> services = new HashSet<WorkItem>();
        
        WorkItem service1 = new WorkItem();
        service1.setDuration(50);
        service1.setPrice(20);
        
        WorkItem service2 = new WorkItem();
        service2.setDuration(10);
        service2.setPrice(10);
        
        services.add(service1);
        services.add(service2);
        
        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setTechnician(technician1);
        appointment1.setDate(new java.sql.Date(0));
        appointment1.setStartTime(new java.sql.Time(63000000)); //7:30PM
        appointment1.setEndTime(new java.sql.Time(64800000)); //8PM
        appointment1.setCustomer(customer);
        appointment1.setWorkItem(services);
        
        return appointment1;
      }
      else {
        return null;
      }
      
  });
    
    lenient().when(appointmentDao.save(any(Appointment.class))).thenAnswer((InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
  });
    
    lenient().doAnswer(invocation -> {
      return invocation.getArgument(0);
  }).when(appointmentDao).delete(any(Appointment.class));
    
    lenient().when(appointmentDao.findAppointmentByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
      if(((Customer) invocation.getArgument(0)).getUsername().equals(USERNAME1)) {
        
          Customer customer = new Customer();
          customer.setUsername(USERNAME1);
          customer.setPassword(PASSWORD1);
          customer.setName(NAME1);
          customer.setEmail(EMAIL1);
        
          Set<Appointment> result = new HashSet<Appointment>();
          
          Appointment appointment1 = new Appointment();
          appointment1.setCustomer(customer);
          
          result.add(appointment1);
        
          return result;
          
      } else {
        Set<Appointment> result = new HashSet<Appointment>();
        return result;
      }
  });
    
    lenient().when(technicianDao.findTechnicianByTechnicianHour(any(TechnicianHour.class))).thenAnswer((InvocationOnMock invocation) -> {

      if(((TechnicianHour) invocation.getArgument(0)).getId() == 1) {
          return technician1;
      }
      else if (((TechnicianHour) invocation.getArgument(0)).getId() == 2){
        return technician2;
      }
      else {
        return null;
      }
      
  });
    
    lenient().when(technicianDao.findTechnicianByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {

      if(invocation.getArgument(0).equals(USERNAME2)) {
          return technician1;
      }
      else {
        return null;
      }
      
  });
    
    lenient().when(appointmentDao.findAppointmentByTechnician(any(Technician.class))).thenAnswer((InvocationOnMock invocation) -> {
      if(((Technician) invocation.getArgument(0)).getUsername().equals(USERNAME2)) {
        
          Technician technician = new Technician();
          technician.setUsername(USERNAME2);
          technician.setPassword(PASSWORD2);
          technician.setName(NAME2);
          technician.setEmail(EMAIL2);
        
          Set<Appointment> result = new HashSet<Appointment>();
          
          Appointment appointment1 = new Appointment();
          appointment1.setTechnician(technician);
          
          result.add(appointment1);
        
          return result;
          
      } else {
        Set<Appointment> result = new HashSet<Appointment>();
        return result;
      }
  });
    
    lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
      if(invocation.getArgument(0).equals(USERNAME1)){
        
              Customer customer = new Customer();
              customer.setUsername(USERNAME1);
              customer.setPassword(PASSWORD1);
              customer.setName(NAME1);
              customer.setEmail(EMAIL1);
              return customer;
          
      } else {
          return null;
      }
  });
    
    //view available appointments
    lenient().when(technicianHourDao.findTechnicianHourByDate(any(java.sql.Date.class))).thenAnswer((InvocationOnMock invocation) -> {
      
      //situation 1
      if(((java.sql.Date) invocation.getArgument(0)).equals(new java.sql.Date(0))) {
        
          WorkBreak workBreak1 = new WorkBreak();
          workBreak1.setStartBreak(new java.sql.Time(46800000)); //3PM
          workBreak1.setEndBreak(new java.sql.Time(48600000));  //3:30PM
          
          WorkBreak workBreak2 = new WorkBreak();
          workBreak2.setStartBreak(new java.sql.Time(70200000)); //9:30PM
          workBreak2.setEndBreak(new java.sql.Time(72000000));  //10PM
          
          Set<WorkBreak> workBreaks1 = new HashSet<WorkBreak>();
          workBreaks1.add(workBreak1);
          workBreaks1.add(workBreak2);
          
          TechnicianHour technicianHour1 = new TechnicianHour();
          technicianHour1.setId(1); //just for testing purposes for the mock
          //technicianHour1.setTechnician(technician1);
          
          Set<TechnicianHour> technicianHourSet1 = new HashSet<TechnicianHour>();
          
          technicianHour1.setDate(new java.sql.Date(0));
          technicianHour1.setStartTime(new java.sql.Time(43200000)); //2PM
          technicianHour1.setEndTime(new java.sql.Time(72000000)); //10PM
          technicianHour1.setWorkBreak(workBreaks1);
          
          technicianHourSet1.add(technicianHour1);
          
          technician1.setTechnicianHour(technicianHourSet1);
          
          //
          WorkBreak workBreak3 = new WorkBreak();
          workBreak3.setStartBreak(new java.sql.Time(43200000)); //2PM
          workBreak3.setEndBreak(new java.sql.Time(48600000));  //3:30PM
          
          Set<WorkBreak> workBreaks2 = new HashSet<WorkBreak>();
          workBreaks2.add(workBreak3);
          
          TechnicianHour technicianHour2 = new TechnicianHour();
          technicianHour2.setId(2);
          //technicianHour2.setTechnician(technician2);
          
          technicianHour2.setDate(new java.sql.Date(0));
          technicianHour2.setStartTime(new java.sql.Time(43200000)); //2PM
          technicianHour2.setEndTime(new java.sql.Time(72000000)); //10PM
          technicianHour2.setWorkBreak(workBreaks2);
          
          Set<TechnicianHour> technicianHourSet2 = new HashSet<TechnicianHour>();
          technicianHourSet2.add(technicianHour2);
          
          technician2.setTechnicianHour(technicianHourSet2);
        
          //
          Set<TechnicianHour> result = new HashSet<TechnicianHour>();
          
          result.add(technicianHour1);
          result.add(technicianHour2);
        
          return result;
          
      } else {
        List<TechnicianHour> result = new ArrayList<TechnicianHour>();
        return result;
      }
  });
    
    
    lenient().when(appointmentDao.findAppointmentByDate(any(java.sql.Date.class))).thenAnswer((InvocationOnMock invocation) -> {
      
      //situation 1
      if(((java.sql.Date) invocation.getArgument(0)).equals(new java.sql.Date(0))) {
        
        Appointment appointment1 = new Appointment();
        appointment1.setTechnician(technician1);
        appointment1.setDate(new java.sql.Date(0));
        appointment1.setStartTime(new java.sql.Time(63000000)); //7:30PM
        appointment1.setEndTime(new java.sql.Time(64800000)); //8PM
        
        Appointment appointment2 = new Appointment();
        appointment2.setTechnician(technician1);
        appointment2.setDate(new java.sql.Date(0));
        appointment2.setStartTime(new java.sql.Time(64800000)); //8PM
        appointment2.setEndTime(new java.sql.Time(66600000)); //8:30PM
        
        Appointment appointment3 = new Appointment();
        appointment3.setTechnician(technician2);
        appointment3.setDate(new java.sql.Date(0));
        appointment3.setStartTime(new java.sql.Time(63000000)); //7:30PM
        appointment3.setEndTime(new java.sql.Time(64800000)); //8PM
        
        Set<Appointment> appointments = new HashSet<Appointment>();
        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);
        
        return appointments;
        
      } else {
        Set<Appointment> result = new HashSet<Appointment>();
        return result;
      }
  });
    
  }
  
  @Test
  public void testGetAllAppointments() {
    
    //preparation
    List<Appointment> appointments = new ArrayList<Appointment>();
    
    //do I have to add associated objects?
    Appointment appointment1 = new Appointment();
    Appointment appointment2 = new Appointment();
    
    appointments.add(appointment1);
    appointments.add(appointment2);
    
    lenient().when(appointmentDao.findAll()).thenReturn(appointments);
    
    //testing
    List<Appointment> result = service.getAllAppointments();
    
    assertEquals(result.size(), 2);
    assertTrue(result.contains(appointment1));
    assertTrue(result.contains(appointment2));
  }
  
  @Test
  public void testGetAppointmentsByCustomer() {
    
    List<Appointment> result = new ArrayList<Appointment>();
    String error = "";
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME1);
    customer.setPassword(PASSWORD1);
    customer.setName(NAME1);
    customer.setEmail(EMAIL1);
    
    try{
      result = service.getAppointmentsByCustomer(customer);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertEquals(error, "");
    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getCustomer().getUsername(), customer.getUsername());
  }
  
  @Test
  public void testGetAppointmentsByCustomerNullArgument() {
    
    List<Appointment> result = new ArrayList<Appointment>();
    
    String error = "";
    
    try{
      result = service.getAppointmentsByCustomer(null);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertEquals(result.size(), 0);
    assertEquals(error, "java.lang.IllegalArgumentException: Invalid customer");
  }
  
  @Test
  public void testGetAppointmentsByCustomerDoesNotExist() {
    
    List<Appointment> result = new ArrayList<Appointment>();
    String error = "";
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME2);
    customer.setPassword(PASSWORD2);
    customer.setName(NAME2);
    customer.setEmail(EMAIL2);
    
    try{
      result = service.getAppointmentsByCustomer(customer);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertEquals(error, "java.lang.IllegalArgumentException: Customer does not exist");
    assertEquals(result.size(), 0);
    
  }
  
  @Test
  public void testGetAppointmentsByDate() {
    List<Appointment> result = service.getAppointmentsByDate(new java.sql.Date(0));
    
    assertEquals(result.size(), 3);
    assertEquals(result.get(0).getDate(), new java.sql.Date(0));
    assertEquals(result.get(1).getDate(), new java.sql.Date(0));
    assertEquals(result.get(2).getDate(), new java.sql.Date(0));
  }
  
  @Test
  public void testGetAppointmentsByTechnician() {
    
    List<Appointment> result = new ArrayList<Appointment>();
    String error = "";
    
    Technician technician = new Technician();
    technician.setUsername(USERNAME2);
    technician.setPassword(PASSWORD2);
    technician.setName(NAME2);
    technician.setEmail(EMAIL2);
    
    try{
      result = service.getAppointmentsByTechnician(technician);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertEquals(error, "");
    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getTechnician().getUsername(), technician.getUsername());
  }
  
  @Test
  public void testGetAppointmentsByTechnicianNullArgument() {
    
    List<Appointment> result = new ArrayList<Appointment>();
    
    String error = "";
    
    try{
      result = service.getAppointmentsByTechnician(null);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertEquals(result.size(), 0);
    assertEquals(error, "java.lang.IllegalArgumentException: Invalid technician");
  }
  
  @Test
  public void testGetAppointmentsByTechnicianDoesNotExist() {
    
    List<Appointment> result = new ArrayList<Appointment>();
    String error = "";
    
    Technician technician = new Technician();
    technician.setUsername(USERNAME1);
    technician.setPassword(PASSWORD1);
    technician.setName(NAME1);
    technician.setEmail(EMAIL1);
    
    try{
      result = service.getAppointmentsByTechnician(technician);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertEquals(error, "java.lang.IllegalArgumentException: Technician does not exist");
    assertEquals(result.size(), 0);
    
  }
  
  @Test
  public void testCreateAppointment() {
    
    String error = "";
        
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME1);
    customer.setPassword(PASSWORD1);
    customer.setName(NAME1);
    customer.setEmail(EMAIL1);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, customer, technician1, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNotNull(appointment);
    assertEquals(appointment.getEndTime(), new java.sql.Time(52200000));
    assertEquals(error, "");
    assertEquals(customer.getAmountOwed(), 130);
    assertEquals(appointment.getWorkItem(), services);
  }
  
  @Test
  public void testCreateAppointmentNotAvailable() {
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME1);
    customer.setPassword(PASSWORD1);
    customer.setName(NAME1);
    customer.setEmail(EMAIL1);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, customer, technician1, new java.sql.Time(0), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: Technician not available during specified time");
  }
  
  @Test
  public void testCreateAppointmentDateNullArgument() {
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME1);
    customer.setPassword(PASSWORD1);
    customer.setName(NAME1);
    customer.setEmail(EMAIL1);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, customer, technician1, new java.sql.Time(48600000), null);
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: Invalid date and time");
  }
  
  @Test
  public void testCreateAppointmentTechnicianDoesNotExist() {
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME1);
    customer.setPassword(PASSWORD1);
    customer.setName(NAME1);
    customer.setEmail(EMAIL1);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, customer, technician2, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: Technician does not exist");
  }
  
  @Test
  public void testCreateAppointmentCustomerDoesNotExist() {
    
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME2);
    customer.setPassword(PASSWORD2);
    customer.setName(NAME2);
    customer.setEmail(EMAIL2);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, customer, technician1, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: Customer does not exist");
  }
  
  @Test
  public void testCreateAppointmentNoServices() {
    
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME2);
    customer.setPassword(PASSWORD2);
    customer.setName(NAME2);
    customer.setEmail(EMAIL2);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    try {
      appointment = service.createAppointment(services, customer, technician1, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: No services specified");
  }
  
  @Test
  public void testCreateAppointmentCustomerNullArgument() {
    String error = "";
    
    Appointment appointment = null;
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, null, technician1, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: Invalid customer");
  }
  
  @Test
  public void testCreateAppointmentTechnicianNullArgument() {
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME2);
    customer.setPassword(PASSWORD2);
    customer.setName(NAME2);
    customer.setEmail(EMAIL2);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    
    WorkItem service2 = new WorkItem();
    service2.setDuration(10);
    service2.setPrice(10);
    
    services.add(service1);
    services.add(service2);
    
    try {
      appointment = service.createAppointment(services, customer, null, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: Invalid technician");
  }
  
  @Test
  public void testCreateAppointmentWorkItemNullArgument() {
    String error = "";
    
    Appointment appointment = null;
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME2);
    customer.setPassword(PASSWORD2);
    customer.setName(NAME2);
    customer.setEmail(EMAIL2);
    customer.setAmountOwed(100);
    
    try {
      appointment = service.createAppointment(null, customer, technician1, new java.sql.Time(48600000), new java.sql.Date(0));
    }
    catch (Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment);
    assertEquals(error, "java.lang.IllegalArgumentException: No services specified");
  }
  
  @Test
  public void testDeleteAppointment() {
    
    String error = "";
    
    Appointment appointment1 = new Appointment();
    appointment1.setId(1);
    
    Appointment appointment2 = null;
    
    try {
      appointment2 = service.deleteAppointment(appointment1.getId());
    }
    catch(Exception e) {
      error = e.toString();
    }
    
    assertNotNull(appointment2);
    assertEquals(error, "");
    assertEquals(appointment2.getCustomer().getAmountOwed(), 70);
    assertEquals(appointment1.getId(), appointment2.getId());
  }
  
  @Test
  public void testDeleteAppointmentDoesNotExist() {
    
    String error = "";
    
    Appointment appointment1 = new Appointment();
    appointment1.setId(2);
    
    Appointment appointment2 = null;
    
    try {
      appointment2 = service.deleteAppointment(appointment1.getId());
    }
    catch(Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment2);
    assertEquals(error, "java.lang.IllegalArgumentException: Appointment does not exist");
  }
  
  @Test
  public void testDeleteAppointmentNullArgument() {
    
    String error = "";
    
    Appointment appointment2 = null;
    
    try {
      appointment2 = service.deleteAppointment(null);
    }
    catch(Exception e) {
      error = e.toString();
    }
    
    assertNull(appointment2);
    assertEquals(error, "java.lang.IllegalArgumentException: Invalid ID");
  }
  
  //will the object references be the same in real scenario
  
  @Test
  public void testGetTechnicianAvailableTechnicianHoursByDate() {
    
    Map<Technician, List<TechnicianHour>> result = service.getTechnicianAvailableTechnicianHoursByDate(new java.sql.Date(0), 120);
    
    assertEquals(result.size(), 2);
    
    assertEquals(result.get(technician1).size(), 1);
    assertEquals(result.get(technician1).get(0).getStartTime(), new java.sql.Time(48600000));
    assertEquals(result.get(technician1).get(0).getEndTime(), new java.sql.Time(63000000));
    
    assertEquals(result.get(technician2).size(), 2);
    assertEquals(result.get(technician2).get(0).getStartTime(), new java.sql.Time(48600000));
    assertEquals(result.get(technician2).get(0).getEndTime(), new java.sql.Time(63000000));
    assertEquals(result.get(technician2).get(1).getStartTime(), new java.sql.Time(64800000));
    assertEquals(result.get(technician2).get(1).getEndTime(), new java.sql.Time(72000000));
    
    //technician1:
    //getTechnicianHoursByDate: 14:00-22:00, breaks 15:00-15:30 and 21:30-22:00
    //getTechnicianAppointmentsByDate: 19:30-20:00, 20:00-20:30
    //cleanupAppointments: 19:30-20:30
    //subtractWorkBreaksFromTechnicianHours: 14:00-15:00, 15:30-21:30
    //subtractAppointmentsFromTechnicianHours: 14:00-15:00, 15:30-19:30, 20:30-21:30
    //filterByMinDuration: 15:30-19:30
    //-> result: 15:30-19:30  
    
    //technician2:
    //getTechnicianHoursByDate: 14:00-22:00, break 14:00-15:30
    //getTechnicianAppointmentsByDate: 19:30-20:00
    //cleanupAppointments: 19:30-20:00
    //subtractWorkBreaksFromTechnicianHours: 15:30-22:00
    //subtractAppointmentsFromTechnicianHours: 15:30-19:30, 20:00-22:00
    //filterByMinDuration: 15:30-19:30, 20:00-22:00
    //-> result: 15:30-19:30, 20:00-22:00
    
  }
  
  @Test
  public void testUpdateAppointment() {
    
    String error = "";
    
    Customer customer = new Customer();
    customer.setUsername(USERNAME1);
    customer.setPassword(PASSWORD1);
    customer.setName(NAME1);
    customer.setEmail(EMAIL1);
    customer.setAmountOwed(100);
    
    Set<WorkItem> services = new HashSet<WorkItem>();
    
    WorkItem service1 = new WorkItem();
    service1.setDuration(50);
    service1.setPrice(20);
    services.add(service1);
    
    Appointment updated = null;
    
    try {
      updated = service.updateAppointment(1, technician1, services, new java.sql.Time(57600000), new java.sql.Date(0));
    }
    catch(Exception e) {
      error = e.toString();
    }
    
    assertEquals(error, "");
    assertNotNull(updated);
    assertEquals(updated.getWorkItem().size(), 1);
    assertEquals(updated.getCustomer().getAmountOwed(), 90);
  }
}
