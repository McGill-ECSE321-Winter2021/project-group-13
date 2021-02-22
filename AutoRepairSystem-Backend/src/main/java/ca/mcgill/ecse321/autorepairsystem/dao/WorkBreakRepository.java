package ca.mcgill.ecse321.autorepairsystem.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.*;

import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;

public interface WorkBreakRepository extends CrudRepository<WorkBreak, String>{

}