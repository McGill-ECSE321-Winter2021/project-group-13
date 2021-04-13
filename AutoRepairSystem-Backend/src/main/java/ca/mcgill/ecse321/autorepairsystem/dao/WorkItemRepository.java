package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autorepairsystem.model.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, String>{
	WorkItem findWorkItemByName(String name);
}