package ca.mcgill.ecse321.autorepairsystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autorepairsystem.dto.AdministratorDto;
import ca.mcgill.ecse321.autorepairsystem.dto.AppointmentDto;
import ca.mcgill.ecse321.autorepairsystem.dto.BusinessHourDto;
import ca.mcgill.ecse321.autorepairsystem.dto.CustomerDto;
import ca.mcgill.ecse321.autorepairsystem.dto.EndUserDto;
import ca.mcgill.ecse321.autorepairsystem.dto.TechnicianDto;
import ca.mcgill.ecse321.autorepairsystem.dto.TechnicianHourDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkBreakDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkHourDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkItemDto;
import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.BusinessHour;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.EndUser;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.WorkHour;
import ca.mcgill.ecse321.autorepairsystem.model.WorkItem;
import ca.mcgill.ecse321.autorepairsystem.service.AutoRepairSystemService;

@CrossOrigin(origins = "*")
@RestController
public class AutoRepairSystemController {
	
	@Autowired
	private AutoRepairSystemService service;
	
	
	//Customer controller methods
	
	@GetMapping(value = { "/customers", "/customers/" })
	public ResponseEntity<?> getAllCustomers() {
		return new ResponseEntity<>(service.getAllCustomers().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/customers/{username}", "/customers/{username}/"})
	public ResponseEntity<?> getCustomer(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.getCustomerByUsername(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@PostMapping(value = { "/customers/{username}", "/customers/{username}/" })
	public ResponseEntity<?> createCustomer(@PathVariable("username") String username, 
			@RequestParam String password, 
			@RequestParam String name, 
			@RequestParam String email) throws IllegalArgumentException {
		try {
			Customer customer = service.createCustomer(username, password, name, email);
			return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	//Administrator controller methods
	
	@GetMapping(value = { "/administrators", "/administrators/" })
	public ResponseEntity<?> getAllAdministrators() {
		return new ResponseEntity<>(service.getAllAdministrators().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/administrators/{username}", "administrators/{username}/"})
	public ResponseEntity<?> getAdministrator(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.getAdministrator(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = {"/make/{username}/administrator", "/make/{username}/administrator/"})
	public ResponseEntity<?> makeAdministrator(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.makeAdministrator(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = {"/make/{username}/technician", "/make/{username}/technician/"})
	public ResponseEntity<?> makeTechnician(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.makeTechnician(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//User controller methods
	
	@GetMapping(value = { "/endusers", "/endusers/" })
	public ResponseEntity<?> getAllEndUsers() {
		return new ResponseEntity<>(service.getAllEndUsers().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/endusers/{username}", "/endusers/{username}/"})
	public ResponseEntity<?> getEndUser(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.getEndUser(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping(value = {"/endusers/{username}", "/endusers/{username}/"})
	public ResponseEntity<?> updateEndUser(@PathVariable("username") String username, 
			@RequestParam String password, 
			@RequestParam String name, 
			@RequestParam String email) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateEndUser(username, password, name, email)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping(value = {"/endusers/{username}","/endusers/{username}/"})
	public ResponseEntity<?> deleteEndUser(@PathVariable("username") String username){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteEndUser(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//Technician controller methods
	
	@GetMapping(value = { "/technicians", "/technicians/" })
  	public ResponseEntity<?> getAllTechnicians() {
  		return new ResponseEntity<>(service.getAllTechnicians().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
  	}
	
	
	@GetMapping(value = { "/technicians/{username}", "/technicians/{username}/"})
  	public ResponseEntity<?> getTechnician(@PathVariable("username") String username) {
  		try {
  			return new ResponseEntity<>(convertToDto(service.getTechnicianByUsername(username)), HttpStatus.OK);
  		}
  		catch (IllegalArgumentException e) {
  			return ResponseEntity.badRequest().body(e.getMessage());
  		}
  	}
	
	
	//Sign in controller methods
	
	@GetMapping(value = {"/signin", "/signin/"})
	public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password) {
		try {
			return new ResponseEntity<>(convertToDto(service.signIn(username, password)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	//workBreak controller methods
	
	@GetMapping(value = { "/workbreaks/{id}", "/workbreaks/{id}/" })
	public ResponseEntity<?> getWorkBreak(@PathVariable("id") Integer id) throws IllegalArgumentException {
		try {
			WorkBreak workbreak = service.getWorkBreak(id);
			return new ResponseEntity<>(convertToDto(workbreak), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = { "/workbreaks/{workHourId}", "/workbreaks/{workHourId}/" })
	public ResponseEntity<?> createWorkBreak(@PathVariable("workHourId") Integer workHourId,
			@RequestParam Time startBreak,  
			@RequestParam Time  endBreak) throws IllegalArgumentException {
		try {
			WorkBreak workbreak = service.createWorkBreak(workHourId, startBreak, endBreak);
			return new ResponseEntity<>(convertToDto(workbreak), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping(value = { "/workbreaks/{id}", "/workbreaks/{id}/"})
	public ResponseEntity<?> updateWorkBreak(@PathVariable("id") Integer id,
			@RequestParam Time newStartTime,
			@RequestParam Time newEndTime) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkBreak(id, newStartTime, newEndTime)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping(value = {"/workbreaks/{id}", "/workbreaks/{id}/"})
	public ResponseEntity<?> DeleteWorkBreak(@PathVariable("id") Integer id){
		try {
			return new ResponseEntity<>(convertToDto(service.DeleteWorkBreak(id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	//work Item Controller Methods
	
	@GetMapping(value = { "/workitems", "/workitems/"})
	public ResponseEntity<?> getAllWorkItems() {
		return new ResponseEntity<>(service.getAllWorkItems().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/workitems/{name}", "/workitems/{name}/" })
	public ResponseEntity<?> getWorkItem(@PathVariable("name") String name) throws IllegalArgumentException {
		try {
			WorkItem workitem = service.getWorkItem(name);
			return new ResponseEntity<>(convertToDto(workitem), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = { "/workitems/{name}", "/workbreaks/{name}/" })
	public ResponseEntity<?> createWorkItem(@PathVariable("name") String name, 
			@RequestParam int duration, 
			@RequestParam int  price) throws IllegalArgumentException {
		try {
			WorkItem workitem = service.createWorkItem(name, duration, price);
			return new ResponseEntity<>(convertToDto(workitem), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping(value = { "/workitems/{name}", "/workitems/{name}/"})
	public ResponseEntity<?> updateWorkItem(@PathVariable("name") String name,
			@RequestParam int duration,
			@RequestParam int price) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkItem(name,duration,price)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping(value = {"/workitem/{name}", "/workitem/{name}/"})
	public ResponseEntity<?> DeleteWorkItem(@PathVariable("name") String name){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteWorkItem(name)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	//business Hour Controller methods
	
	@GetMapping(value = { "/businesshours", "/businesshours/"})
	public ResponseEntity<?> getAllBusinessHours() {
		return new ResponseEntity<>(service.getAllBusinessHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/businesshours/{id}", "/businesshours/{id}/" })
	public ResponseEntity<?> getBusinessHour(@PathVariable("id") Integer id
			) throws IllegalArgumentException {
		try {
			BusinessHour businessHour = service.getBusinessHour(id);
			return new ResponseEntity<>(convertToDto(businessHour), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = { "/businesshours/{date}", "/businesshours/{date}/" })
	public ResponseEntity<?> createBusinessHour(@PathVariable("date") Date date, 
			@RequestParam Time start, 
			@RequestParam Time  end) throws IllegalArgumentException {
		try {
			BusinessHour businesshour = service.createBusinessHour(start, end, date);
			return new ResponseEntity<>(convertToDto(businesshour), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping(value = { "/businesshours/{id}", "/businesshours/{id}/"})
	public ResponseEntity<?> updateBusinessHour(@PathVariable("id") Integer id,
			@RequestParam Time start,
			@RequestParam Time end,
			@RequestParam Date date) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateBusinessHour(id, start, end, date)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	
	@DeleteMapping(value = {"/businesshours/{id}", "/businesshours/{id}/"})
	public ResponseEntity<?> DeleteBusinessHour(@PathVariable("id") Integer id){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteBusinessHour(id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//Technician hour controller methods

	@GetMapping(value = { "/technicianhours/{id}", "/technicianhours/{id}/" })
	public ResponseEntity<?> getTechnicianHourById(@PathVariable("id") Integer id) throws IllegalArgumentException {
		try {
			return new ResponseEntity<>(convertToDto(service.getTechnicianHour(id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = { "/technicianhours/{technicianUsername}", "/technicianhours/{technicianUsername}/"})
	public ResponseEntity<?> createTechnicianHour(@PathVariable("technicianUsername") String technicianUsername,
			@RequestParam Time start,
			@RequestParam Time end,
			@RequestParam Date date) {
		try {
			return new ResponseEntity<>(convertToDto(service.createTechnicianHour(technicianUsername, start, end, date)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/technicianhours/{id}", "/technicianhours/{id}/"})
	public ResponseEntity<?> updateTechnicianHour(@PathVariable("id") Integer id,
			@RequestParam Time start,
			@RequestParam Time end,
			@RequestParam Date date) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateTechnicianHour(id, start, end, date)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = {"/technicianhours/{id}", "/technicianhours/{id}/"})
	public ResponseEntity<?> DeleteTechnicianHour(@PathVariable("id") Integer id){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteTechnicianHour(id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//Payment controller methods
	
	@PutMapping(value = { "/customers/{username}/payment", "/customers/{username}/payment/"})
	public ResponseEntity<?> payment(@PathVariable("username") String username,
			@RequestParam Integer amount) {
		try {
			return new ResponseEntity<>(convertToDto(service.payment(username, amount)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//Convert to Dto methods
	
	private BusinessHourDto convertToDto(BusinessHour u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such EndUser!");
		}
		BusinessHourDto BusinessHourDto = new BusinessHourDto(u.getStartTime(), u.getEndTime(), u.getDate(), u.getId(),u.getWorkBreak().stream().map(p -> convertToDto(p)).collect(Collectors.toSet()));
		return BusinessHourDto;
	}
	
	private WorkItemDto convertToDto(WorkItem u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such EndUser!");
		}
		WorkItemDto WorkItemDto = new WorkItemDto(u.getName(), u.getDuration(), u.getPrice());
		return WorkItemDto;
	}
	

	private EndUserDto convertToDto(EndUser u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such EndUser!");
		}
		EndUserDto userDto = new EndUserDto(u.getUsername(), u.getPassword(), u.getName(), u.getEmail());
		return userDto;
	}
	
	private AdministratorDto convertToDto(Administrator a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Administrator!");
		}
		AdministratorDto administratorDto = new AdministratorDto(a.getUsername(), a.getPassword(), a.getName(), a.getEmail());
		return administratorDto;
	}
	
	private CustomerDto convertToDto(Customer c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDto customerDto = new CustomerDto(c.getUsername(), c.getPassword(), c.getName(), c.getEmail(), c.getAmountOwed());
		return customerDto;
	}
	
	private TechnicianDto convertToDto(Technician t) {
		if (t == null) {
			throw new IllegalArgumentException("There is no such Technician!");
		}
		TechnicianDto technicianDto = new TechnicianDto(t.getUsername(), t.getPassword(), t.getName(), t.getEmail(), t.getTechnicianHour().stream().map(p -> convertToDto(p)).collect(Collectors.toSet()));
		return technicianDto;
	}
	
	private TechnicianHourDto convertToDto(TechnicianHour th) {
		if (th == null) {
			throw new IllegalArgumentException("There is no such TechnicianHour!");
		}
		TechnicianHourDto technicianHourDto = new TechnicianHourDto(th.getStartTime(), th.getEndTime(), th.getDate(), th.getId(), th.getWorkBreak().stream().map(p -> convertToDto(p)).collect(Collectors.toSet()));
		return technicianHourDto;
	}
	
	private WorkBreakDto convertToDto(WorkBreak u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such WorkBreak!");
		}
		WorkBreakDto workBreakDto = new WorkBreakDto(u.getStartBreak(), u.getEndBreak(), u.getId());
		return workBreakDto;
		
	}
	
	private AppointmentDto convertToDto(Appointment a) {
	  if (a == null) {
	    throw new IllegalArgumentException("There is no such Appointment!");
	  }
	  
	  Set<WorkItemDto> s = a.getWorkItem().stream().map(p -> convertToDto(p)).collect(Collectors.toSet());
	  
	  AppointmentDto appointmentDto = new AppointmentDto(s, convertToDto(a.getTechnician()), convertToDto(a.getCustomer()), a.getId(), a.getStartTime(), a.getEndTime(), a.getDate());
	  return appointmentDto;
	}
	
	
}