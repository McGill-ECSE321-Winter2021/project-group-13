package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.EndUser;

public interface EndUserRepository extends CrudRepository<EndUser, String>{
	EndUser findEndUserByUsername(String username);
	EndUser findEndUserByEmail(String email);
}