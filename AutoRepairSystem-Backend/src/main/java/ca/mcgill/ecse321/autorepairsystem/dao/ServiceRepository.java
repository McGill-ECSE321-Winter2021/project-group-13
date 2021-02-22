package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String>{
	Service findServiceByname(String name);
}