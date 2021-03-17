package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.EndUser;

public interface UserRepository extends CrudRepository<EndUser, String>{
	EndUser findUserByUsername(String username);
	EndUser findUserByEmail(String email);
}