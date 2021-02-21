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
import ca.mcgill.ecse321.autorepairsystem.model.Business;
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
	public Business createBusiness(AppointmentManager appointmentManager) {
		Business b = new Business(appointmentManager);
		entityManager.persist(b);
		return b;
	}

	@Transactional
	public Business getBusiness(Integer id) {
		Business b = entityManager.find(Business.class, id);
		return b;
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

