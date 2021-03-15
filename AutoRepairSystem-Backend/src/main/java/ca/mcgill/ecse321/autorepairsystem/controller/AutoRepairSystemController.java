/*package ca.mcgill.ecse321.autorepairsystem.controller;

import java.sql.Date;
import java.sql.Time;
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

import ca.mcgill.ecse321.autorepairsystem.dto.UserDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkBreakDto;
import ca.mcgill.ecse321.autorepairsystem.dto.WorkHourDto;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.User;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.WorkHour;
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
	
	
	//User controller methods
	
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
	
	@PutMapping(value = { "/workhour/{Id}", "/workhour/{Id}/"})
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
	
	@PutMapping(value = { "/workhour/{Id}", "/workhour/{Id}/"})
	public ResponseEntity<?> updateWorkHourBreak(@PathVariable("Id") Integer Id,  
			@RequestParam Set<WorkBreak> workBreak) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkHourWorkBreak(Id, workBreak)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/workhour/{Id}", "/workhour/{Id}/"})
	public ResponseEntity<?> deleteWorkHour(@PathVariable("Id") Integer Id){
		try {
			return new ResponseEntity<>(convertToDto(service.deleteWorkHour(Id)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/workhour", "/workhour/"})
	public ResponseEntity<?> getAllWorkHours() {
		return new ResponseEntity<>(service.getAllWorkHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	//workBreak controller methods
	
	
	@PostMapping(value = { "/workbreak/{startbreak}", "/workbreak/{startbreak}/" })
	public ResponseEntity<?> createWorkBreak(@PathVariable("startbreak") Time startbreak, 
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
	
	@PostMapping(value = { "/workbreak/{startbreak}", "/workbreak/{startbreak}/" })
	public ResponseEntity<?> getWorkBreak(@PathVariable("startbreak") Time startbreak, 
			@RequestParam Integer workhourid 
			) throws IllegalArgumentException {
		try {
			WorkBreak workbreak = service.getWorkBreak(workhourid, startbreak);
			return new ResponseEntity<>(convertToDto(workbreak), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/workbreak/{startbreak}", "/workbreak/{startbreak}/"})
	public ResponseEntity<?> updateWorkBreak(@PathVariable("starttime") Integer workhourId, 
			@RequestParam Time newstarttime,
			@RequestParam Time starttime, 
			@RequestParam Time newendtime) {
		try {
			return new ResponseEntity<>(convertToDto(service.updateWorkBreak(workhourId, starttime, newstarttime, newendtime)), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping(value = { "/workbreak/{startbreak}", "/workbreak/{startbreak}/" })
	public ResponseEntity<?> deleteWorkBreak(@PathVariable("startbreak") Time startbreak, 
			@RequestParam Integer workhourid 
			) throws IllegalArgumentException {
		try {
			WorkBreak workbreak = service.DeleteWorkBreak(workhourid, startbreak);
			return new ResponseEntity<>(convertToDto(workbreak), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping(value = { "/workbreak/{Id}", "/workbreak/{Id}/"})
	public ResponseEntity<?> getAllWorkBreaks(@PathVariable("Id") Integer Id) {
		return new ResponseEntity<>(service.getAllWorkBreaks(Id).stream().map(p -> convertToDto(p)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	
	//Convert to Dto methods

	private UserDto convertToDto(User u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such User!");
		}
		UserDto userDto = new UserDto(u.getUsername(), u.getPassword(), u.getName(), u.getEmail());
		return userDto;
	}
	
	
	
	private WorkBreakDto convertToDto(WorkBreak u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such WorkBreak!");
		}
		WorkBreakDto WorkBreakDto = new WorkBreakDto(u.getStartBreak(), u.getEndBreak(), convertToDto(u.getWorkHour()));
		return WorkBreakDto;
		
	}
	
	private WorkHourDto convertToDto(WorkHour u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such WorkHour!");
		}
		WorkHourDto WorkHourDto = new WorkHourDto(u.getStartTime(), u.getEndTime(), u.getDate(), u.getId(), null);
		return WorkHourDto;
		
	}

}
*/