package ca.mcgill.ecse321.autorepairsystem.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autorepairsystem.model.Administrator;
import ca.mcgill.ecse321.autorepairsystem.model.Appointment;
import ca.mcgill.ecse321.autorepairsystem.model.AppointmentManager;
import ca.mcgill.ecse321.autorepairsystem.model.BusinessHour;
import ca.mcgill.ecse321.autorepairsystem.model.Customer;
import ca.mcgill.ecse321.autorepairsystem.model.Service;
import ca.mcgill.ecse321.autorepairsystem.model.Technician;
import ca.mcgill.ecse321.autorepairsystem.model.TechnicianHour;
import ca.mcgill.ecse321.autorepairsystem.model.User;
import ca.mcgill.ecse321.autorepairsystem.model.WorkBreak;
import ca.mcgill.ecse321.autorepairsystem.model.WorkHour;

@Repository
public class AutoRepairSystemRepository {
	@Autowired
	EntityManager entityManager;

	@Transactional
	public Service createService(String name, int duration, int price) {
		Service s = new Service();
		s.setName(name);
		s.setDuration(duration);
		s.setPrice(price);
		entityManager.persist(s);
		return s;
	}

	@Transactional
	public Service getService(String name) {
		Service s = entityManager.find(Service.class, name);
		return s;
	}

	@Transactional
	public AppointmentManager createAppointmentManager() {
		AppointmentManager am = new AppointmentManager();
		entityManager.persist(am);
		return am;
	}

	@Transactional
	public AppointmentManager getAppointmentManager(Integer id) {
		AppointmentManager am = entityManager.find(AppointmentManager.class, id);
		return am;
	}

}

