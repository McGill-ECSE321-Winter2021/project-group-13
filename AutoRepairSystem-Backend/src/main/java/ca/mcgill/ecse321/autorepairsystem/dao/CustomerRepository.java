package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{
	Customer findCustomerByUsername(String username);
	Customer findCustomerByEmail(String email);

}