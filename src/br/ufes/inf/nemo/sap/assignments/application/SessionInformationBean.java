package br.ufes.inf.nemo.sap.assignments.application;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateful session bean implementing the session information component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@SessionScoped
@Stateful
public class SessionInformationBean implements SessionInformation {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Information on the whole application. */
	@EJB
	private SAPInformation sapInformation;
	
	/** The DAO for Spiritist objects. */
	@EJB
	private ProfessorDAO professorDAO;
	
	/** The DAO for Spiritist objects. */
	@EJB
	private StudentDAO studentDAO;

	/** The current professor logged in. */
	private Professor currentProfessor;
	
	/** The current student logged in. */
	private Student currentStudent;
	
	/** Returns the professor logged. */
	@Override
	public Professor getCurrentProfessor() {
		return currentProfessor;
	}
	
	/** Returns the student logged. */
	public Student getCurrentStudent() {
		return currentStudent;
	}

	/** Method used to authenticate professor. */	
	public Professor loginProfessor(String email, String password) {
		/** Variable used to get professor data. */
		Professor user = null;
		
		try {		
			/** Obtains the user given the e-mail address (that serves as username). */	
			user = professorDAO.retrieveByEmail(email);
	
			/** Creates the MD5 hash of the password for comparison. */
			String md5pwd =  sapInformation.transformStringToMd5Hash(password);
	
			/** Checks if the passwords match. */
			String pwd = user.getPassword();
			if ((pwd != null) && (pwd.equals(md5pwd))) {
				/** Login successful. Registers the current user and the type in the session. */		
				currentProfessor = user;			
				pwd = null;
			}
			else {
				/** Login failed. Return null. */
				user = null;
			} 
		} 
		catch (PersistentObjectNotFoundException e) {} 
		catch (MultiplePersistentObjectsFoundException e) {}
		
		/** Returns the professor or null value. */
		return user;
	}
	
	/** Method used to authenticate student. */
	public Student loginStudent(String enrollment, String password) {
		/** Variable used to get student data. */
		Student user = null;
		
		try {
			/** Obtains the user given the e-mail address (that serves as username). */
			user = studentDAO.retrieveByEnrollment(enrollment);
	
			/** Creates the MD5 hash of the password for comparison. */
			String md5pwd =  sapInformation.transformStringToMd5Hash(password);
	
			/** Checks if the passwords match. */
			String pwd = user.getPassword();
			if ((pwd != null) && (pwd.equals(md5pwd))) {
				/** Login successful. Registers the current user in the session. */			
				currentStudent = user;
				pwd = null;
			}
			else {
				/** Login failed. Return null. */
				user = null;
			}
		} 
		catch (PersistentObjectNotFoundException e) {} 
		catch (MultiplePersistentObjectsFoundException e) {}
		
		/** Returns the student or null value. */
		return user;
	}
	
	/** Method called through the data change page to update professor informations. */
	public String updateProfessor(Professor entity) {
		Professor anotherEntity = null;
		
		try {
			/** Verifies that changed the password. */
			if(! entity.getPassword().isEmpty()) {
				/** Creates the MD5 hash for the password. */
				String md5pwd = sapInformation.transformStringToMd5Hash(entity.getPassword());
				
				/** Updates the value of the password. */
				entity.setPassword(md5pwd);
			}
			else {
				/** To keep the old password. */
				anotherEntity = professorDAO.retrieveById(entity.getId());
				entity.setPassword(anotherEntity.getPassword());
			}
			
			/** Rule 1: Cannot have two professors with the same email. */			
			anotherEntity = professorDAO.retrieveByEmail(entity.getEmail());
			
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if ((anotherEntity != null) && (entityID != anotherEntityID)) {
				return "login.form.email.validation";
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		/** Updated database. */
		currentProfessor = professorDAO.merge(entity);
		return "";
	}
	
	/** Method called through the data change page to update student informations. */
	public String updateStudent(Student entity) {
		Student anotherEntity = null;
		
		/** Verifies that changed the password. */
		if(! entity.getPassword().isEmpty()) {
			/** Creates the MD5 hash for the password. */
			String md5pwd = sapInformation.transformStringToMd5Hash(entity.getPassword());
			
			/** Updates the value of the password. */
			entity.setPassword(md5pwd);
		}
		else {
			/** To keep the old password. */
			anotherEntity = studentDAO.retrieveById(entity.getId());
			entity.setPassword(anotherEntity.getPassword());
		}
		
		/** Updated database. */
		currentStudent = studentDAO.merge(entity);
		return "";
	}
}