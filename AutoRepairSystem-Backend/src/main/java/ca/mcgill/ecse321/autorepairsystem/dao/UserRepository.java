package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.User;

public interface UserRepository extends CrudRepository<User, String>{
	User findUserByUsername(String username);
	User findUserByEmail(String email);
}