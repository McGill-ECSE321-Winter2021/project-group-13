package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;


import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;

public interface CustomerRepository extends CrudRepository<Customer, String>{
	Customer findCustomerByUsername(String username);
	Customer findCustomerByAppointment(Appointment appointment);

}