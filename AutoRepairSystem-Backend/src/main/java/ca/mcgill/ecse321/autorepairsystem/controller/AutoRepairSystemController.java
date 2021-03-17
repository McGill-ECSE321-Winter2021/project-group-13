/*package ca.mcgill.ecse321.autorepairsystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
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
import ca.mcgill.ecse321.autorepairsystem.dto.BusinessHourDto;
import ca.mcgill.ecse321.autorepairsystem.dto.CustomerDto;
import ca.mcgill.ecse321.autorepairsystem.dto.EndUserDto;
import ca.mcgill.ecse321.autorepairsystem.dto.TechnicianDto;
import ca.mcgill.ecse321.autorepairsystem.dto.TechnicianHourDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkBreakDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkHourDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkItemDto;
import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
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
	
	@PutMapping(value = {"/customers/{username}", "/customers/{username}/"})
	public ResponseEntity<?> updateCustomer(@PathVariable("username") String username, 
			@RequestParam String password, 
			@RequestParam String name, 
			@RequestParam String email) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateCustomer(username, password, name, email)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = {"/customers/{username}","/customers/{username}/"})
	public ResponseEntity<?> deleteCustomer(@PathVariable("username") String username){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteCustomer(username)), HttpStatus.OK);
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
	
	
	@GetMapping(value = { "/administrators/{username}", "/administrators/{username}/"})
	public ResponseEntity<?> getAdministrator(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.getAdministrator(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = {"/makeAdministrator/{username}", "/makeAdministrator/{username}/"})
	public ResponseEntity<?> makeAdministrator(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.makeAdministrator(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = {"/administrators/{username}", "/administrators/{username}/"})
  	public ResponseEntity<?> updateAdministrator(@PathVariable("username") String username, 
  			@RequestParam String password, 
  			@RequestParam String name, 
  			@RequestParam String email) {
  		try {
  			return new ResponseEntity<>(convertToDto(service.updateAdministrator(username, password, name, email)), HttpStatus.OK);
  		}
  		catch (IllegalArgumentException e) {
  			return ResponseEntity.badRequest().body(e.getMessage());
  		}
  	}
	
	@DeleteMapping(value = {"/administrators/{username}","/administrators/{username}/"})
  	public ResponseEntity<?> deleteAdministrator(@PathVariable("username") String username){
  		try {
  			return new ResponseEntity<>(convertToDto(service.deleteAdministrator(username)), HttpStatus.OK);
  		}
  		catch (IllegalArgumentException e) {
  			return ResponseEntity.badRequest().body(e.getMessage());
  		}
  	}
	
	
	//User controller methods
	
	@GetMapping(value = { "/users", "/users/" })
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(service.getAllUsers().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	@GetMapping(value = { "/users/{username}", "/users/{username}/"})
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		try {
			return new ResponseEntity<>(convertToDto(service.getUser(username)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = {"/users/{username}", "/users/{username}/"})
	public ResponseEntity<?> updateUser(@PathVariable("username") String username, 
			@RequestParam String password, 
			@RequestParam String name, 
			@RequestParam String email) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateUser(username, password, name, email)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = {"/users/{username}","/users/{username}/"})
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteUser(username)), HttpStatus.OK);
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
	
	@PostMapping(value = { "/technicians/{username}", "/technicians/{username}/" })
  	public ResponseEntity<?> createTechnician(@PathVariable("username") String username, 
  			@RequestParam String password, 
  			@RequestParam String name, 
  			@RequestParam String email) throws IllegalArgumentException {
  		try {
  			Technician technician = service.createTechnician(username, password, name, email);
  			return new ResponseEntity<>(convertToDto(technician), HttpStatus.OK);
  		}
  		catch (IllegalArgumentException e) {
  			return ResponseEntity.badRequest().body(e.getMessage());
  		}
  	}
	
	@PutMapping(value = {"/technicians/{username}", "/technicians/{username}/"})
	public ResponseEntity<?> updateTechnician(@PathVariable("username") String username, 
			@RequestParam String password, 
			@RequestParam String name, 
			@RequestParam String email) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateTechnician(username, password, name, email)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = {"/technicians/{username}","/technicians/{username}/"})
	public ResponseEntity<?> deleteTechnician(@PathVariable("username") String username){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteTechnician(username)), HttpStatus.OK);
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
	
	
	//workHour controller methods
	
	@GetMapping(value = { "/workhour/{Id}", "/workhour/{Id}/"})
	public ResponseEntity<?> getWorkHour(@PathVariable("Id") Integer Id) {
		try {
			return new ResponseEntity<>(convertToDto(service.getWorkHour(Id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping(value = { "/workhour/{Id}", "/workhour/{Id}/"})
	public ResponseEntity<?> updateWorkHour(@PathVariable("Id") Integer Id, 
			@RequestParam Date date, 
			@RequestParam Time starttime, 
			@RequestParam Time endtime) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkHour(Id, starttime, endtime, date)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping(value = { "/workhour/Date/{Id}", "/workhour/Date/{Id}/"})
	public ResponseEntity<?> updateWorkHourBreak(@PathVariable("Id") Integer Id,  
			@RequestParam Set<WorkBreak> workBreak) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkHourWorkBreak(Id, workBreak)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = { "/workhour/Id/{Id}", "/workhour/Id/{Id}/"})
	public ResponseEntity<?> deleteWorkHour(@PathVariable("Id") Integer Id){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteWorkHour(Id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(value = { "/workhour", "/workhour/"})
	public ResponseEntity<?> getAllWorkHours() {
		return new ResponseEntity<>(service.getAllWorkHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	//workBreak controller methods
	
	
	@PostMapping(value = { "/workbreak/{Id}", "/workbreak/{Id}/" })
	public ResponseEntity<?> createWorkBreak(@PathVariable("Id") Time startbreak, 
			@RequestParam Integer workhourid, 
			@RequestParam Time  endbreak) throws IllegalArgumentException {
		try {
			WorkBreak workbreak = service.createWorkBreak(workhourid, startbreak, endbreak);
			return new ResponseEntity<>(convertToDto(workbreak), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(value = { "/workbreak/{Id}", "/workbreak/{Id}/" })
	public ResponseEntity<?> getWorkBreak(@PathVariable("Id") 
			@RequestParam Integer workBreakId
			) throws IllegalArgumentException {
		try {
			WorkBreak workbreak = service.getWorkBreak(workBreakId);
			return new ResponseEntity<>(convertToDto(workbreak), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/workbreak/{Id}", "/workbreak/{Id}/"})
	public ResponseEntity<?> updateWorkBreak(@PathVariable("Id") Integer id,
			@RequestParam Time newstarttime,
			@RequestParam Time newendtime) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkBreak(id, newstarttime, newendtime)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = {"/workbreak/{Id}", "/workbreak/{Id}/"})
	public ResponseEntity<?> DeleteWorkBreak(@PathVariable("Id") Integer Id){
		try {
			return new ResponseEntity<>(convertToDto(service.DeleteWorkBreak(Id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//work Item Controller Methods
	
	@PostMapping(value = { "/workitem/{name}", "/workbreak/{name}/" })
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
	
	@GetMapping(value = { "/workitem/{name}", "/workitem/{name}/" })
	public ResponseEntity<?> getWorkItem(@PathVariable("name") 
			@RequestParam String name
			) throws IllegalArgumentException {
		try {
			WorkItem workitem = service.getWorkItem(name);
			return new ResponseEntity<>(convertToDto(workitem), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/workitem/{name}", "/workitem/{name}/"})
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
	
	@GetMapping(value = { "/workhour", "/workhour/"})
	public ResponseEntity<?> getAllWorkItems() {
		return new ResponseEntity<>(service.getAllWorkItems().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	//business Hour Controller methods
	
	@PostMapping(value = { "/businesshour/{date}", "/businesshour/{date}/" })
	public ResponseEntity<?> createBusinessHour(@PathVariable("name") Date date, 
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
	
	
	@GetMapping(value = { "/businesshour/{Id}", "/businesshour/{Id}/" })
	public ResponseEntity<?> getBusinessHour(@PathVariable("Id") 
			@RequestParam Integer Id
			) throws IllegalArgumentException {
		try {
			BusinessHour businessHour = service.getBusinessHour(Id);
			return new ResponseEntity<>(convertToDto(businessHour), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PutMapping(value = { "/businesshour/{Id}", "/businesshour/{Id}/"})
	public ResponseEntity<?> updateBusinessHour(@PathVariable("Id") Integer Id,
			@RequestParam Time start,
			@RequestParam Time end,
			@RequestParam Date date) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateBusinessHour(Id, start, end, date)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping(value = { "/businesshour/Id/{Id}", "/businesshour/Id/{Id}/"})
	public ResponseEntity<?> updateBusinessHourWorkBreak(@PathVariable("Id") Integer Id,  
			@RequestParam Set<WorkBreak> workBreak) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateBusinessHourWorkBreak(Id, workBreak)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping(value = {"/businesshour/{Id}", "/businesshour/{Id}/"})
	public ResponseEntity<?> DeleteBusinessHour(@PathVariable("Id") Integer Id){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteBusinessHour(Id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@GetMapping(value = { "/workhour", "/workhour/"})
	public ResponseEntity<?> getAllBusinessHours() {
		return new ResponseEntity<>(service.getAllBusinessHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	//Technician hour controller methods
	
	@GetMapping(value = { "/technicianhours", "/technicianhours/"})
	public ResponseEntity<?> getAllTechnicianHours() {
		return new ResponseEntity<>(service.getAllTechnicianHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = { "/technicianhours/{id}", "/technicianhours/{id}/" })
	public ResponseEntity<?> getTechnicianHourById(@PathVariable("id") 
			@RequestParam Integer id
			) throws IllegalArgumentException {
		try {
			TechnicianHour technicianHour = service.getTechnicianHour(id);
			return new ResponseEntity<>(convertToDto(technicianHour), HttpStatus.OK);
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
	
	
	@PostMapping(value = { "/technicianhours/id/{id}", "/technicianhours/id/{id}/"})
	public ResponseEntity<?> updateTechnicianHourWorkBreak(@PathVariable("id") Integer id,  
			@RequestParam Set<WorkBreak> workBreak) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateBusinessHourWorkBreak(id, workBreak)), HttpStatus.OK);
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
		WorkBreakDto WorkBreakDto = new WorkBreakDto(u.getStartBreak(), u.getEndBreak(), u.getId());
		return WorkBreakDto;
		
	}
	
	private WorkHourDto convertToDto(WorkHour u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such WorkHour!");
		}
		WorkHourDto WorkHourDto = new WorkHourDto(u.getStartTime(), u.getEndTime(), u.getDate(), u.getId(), u.getWorkBreak().stream().map(p -> convertToDto(p)).collect(Collectors.toSet()));
		return WorkHourDto;
		
	}
}*/