package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autorepairsystem.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, String>{

	Administrator findAdministratorByUsername(String username);
}
