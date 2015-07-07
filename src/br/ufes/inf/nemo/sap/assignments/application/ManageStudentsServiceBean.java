package br.ufes.inf.nemo.sap.assignments.application;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.sap.lab.domain.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Students" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageStudentsServiceBean 	extends CrudServiceBean<Student>
										implements ManageStudentsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Information on the whole application. */
	@EJB
	private SAPInformation sapInformation;
	
	/** The DAO for Student objects. */
	@EJB
	private StudentDAO studentDAO;
	
	/** The DAO for Supervision objects. */
	@EJB
	private SupervisionDAO supervisionDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;
	
	/** Getter DAO for Student objects. */
	@Override
	public BaseDAO<Student> getDAO() {
		return studentDAO;
	}

	/** Creates a new entity Student. */
	@Override
	protected Student createNewEntity() {
		return new Student();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Student entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Student anotherEntity = null;
			
		try {
			/** Rule 1: Cannot have new student without password. */
			if(entity.getPassword().isEmpty()) {
				/** Occurred some validation error. */
				crudException = addValidationError(crudException, "", "password", "manageStudents.form.password.validation");
				throw crudException;
			}
			else{
				/** Creates the MD5 hash for the password. */
				String md5pwd = sapInformation.transformStringToMd5Hash(entity.getPassword());
				
				/** Updates the value of the password. */
				entity.setPassword(md5pwd);
			}
			
			/** Rule 2: Cannot have two students with the same enrollment. */
			anotherEntity = studentDAO.retrieveByEnrollment(entity.getEnrollment());
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {				
				crudException = addValidationError(crudException, "", "enrollment", "manageStudents.form.enrollment.validation");
				throw crudException;
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(Student entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Student anotherEntity = null;
				
		try {			
			/** Verifies that changed the password. */
			if(! entity.getPassword().isEmpty()) {
				/** Creates the MD5 hash for the password. */
				String md5pwd = sapInformation.transformStringToMd5Hash(entity.getPassword());
				
				/** Updates the value of the new password. */
				entity.setPassword(md5pwd);
			}
			else {
				/** To keep the old password. */
				anotherEntity = studentDAO.retrieveById(entity.getId());
				entity.setPassword(anotherEntity.getPassword());
			}
			
			/** Rule 2: Cannot have two students with the same enrollment. */
			anotherEntity = studentDAO.retrieveByEnrollment(entity.getEnrollment());
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if(anotherEntity != null){
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {			
				crudException = addValidationError(crudException, "", "enrollment", "manageStudents.form.enrollment.validation");
				throw crudException;
			}
		}
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(Student student) {		
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the student has some supervision. */
		Supervision supervisionEntity = null;
		
		try {
			supervisionEntity = supervisionDAO.retrieveByStudentId(student);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "manageStudents.list.validationDelete.supervision";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(supervisionEntity != null) {
			errorMessageKey = "manageStudents.list.validationDelete.supervision";
			return errorMessageKey;
		}
		
		/** Rule 2: Checks if the student has some schoolRoom. */		
		List<SchoolRoom> schoolRooms = schoolRoomDAO.retrieveAll();		
		
		for(SchoolRoom item : schoolRooms){
			List<Student> listStudents = new ArrayList<Student>(item.getStudents());
		
			/** Verifies that occurred some validation error. */
			if(listStudents.contains(student)){
				errorMessageKey = "manageStudents.list.validationDelete.schoolRoom";
				return errorMessageKey;
			}
		}
		
		return errorMessageKey;
	}
	
	/** Returns list of all students. */
	public List<Student> getStudents(){
		return studentDAO.retrieveAll();
	}
}