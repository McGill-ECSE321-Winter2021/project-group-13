package ca.mcgill.ecse321.autorepairsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ca.mcgill.ecse321.autorepairsystem.model.EndUser;

@RestController
@SpringBootApplication
public class AutoRepairSystemApplication {
	
	private static EndUser currentUser;

	public static void main(String[] args) {
	  
	  SpringApplication.run(AutoRepairSystemApplication.class, args);
	 
	}

	@RequestMapping("/")
	public String greeting(){
		return "Hello world! ECSE 321";
	}
	
    public static EndUser getCurrentUser() {
    	if (currentUser != null) {
    		return currentUser;
    	} else {
    		return null;
    	}
    }
    
    public static void setCurrentUser(EndUser curUser) {
    	currentUser = curUser;
    }
}
