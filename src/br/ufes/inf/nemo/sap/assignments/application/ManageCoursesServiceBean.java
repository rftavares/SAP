package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Courses" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageCoursesServiceBean 	extends CrudServiceBean<Course> 
										implements ManageCoursesService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for Course objects. */
	@EJB
	private CourseDAO courseDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;
	
	/** Getter DAO for Course objects. */
	@Override
	public BaseDAO<Course> getDAO() {
		return courseDAO;
	}

	/** Creates a new entity Course. */
	@Override
	protected Course createNewEntity() {
		return new Course();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Course entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;

		/** Rule 1: Cannot have two courses with the same code. */
		try {
			Course anotherEntity = courseDAO.retrieveByCode(entity.getCode());
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {				
				crudException = addValidationError(crudException, "", "code", "manageCourses.form.code.validation");
				throw crudException;
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(Course entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Course anotherEntity = null;
		
		/** Rule 1: Cannot have two courses with the same code. */
		try {
			anotherEntity = courseDAO.retrieveByCode(entity.getCode());
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {	
				crudException = addValidationError(crudException, "", "code", "manageCourses.form.code.validation");
				throw crudException;
			}
		}
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(Course course) {
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the course has some schoolRoom. */
		SchoolRoom schoolRoomEntity = null;
		
		try {
			schoolRoomEntity = schoolRoomDAO.retrieveByCourseId(course);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "manageCourses.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(schoolRoomEntity != null) {
			errorMessageKey = "manageCourses.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		return errorMessageKey;
	}
	
	/** Returns list of all courses. */
	public List<Course> getCourses(){
		return courseDAO.retrieveAll();
	}
}