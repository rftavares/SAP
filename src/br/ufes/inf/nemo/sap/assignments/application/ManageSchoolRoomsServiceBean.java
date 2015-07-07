package br.ufes.inf.nemo.sap.assignments.application;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Schoolrooms" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageSchoolRoomsServiceBean 	extends CrudServiceBean<SchoolRoom>
											implements ManageSchoolRoomsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;
	
	/** The DAO for Assignment objects. */
	@EJB
	private AssignmentDAO assignmentDAO;
	
	/** The DAO for AssignmentGroup objects. */
	@EJB
	private AssignmentGroupDAO assignmentGroupDAO;
	
	/** Getter DAO for SchoolRoom objects. */
	@Override
	public BaseDAO<SchoolRoom> getDAO() {
		return schoolRoomDAO;
	}

	/** Creates a new entity SchoolRoom. */
	@Override
	protected SchoolRoom createNewEntity() {
		return new SchoolRoom();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(SchoolRoom entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;		
		SchoolRoom anotherEntity = null;
		
		/** Rule 1: Cannot have duplication in register of schoolRooms. */
		try {
			anotherEntity = schoolRoomDAO.checksDuplicity(entity);
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		/** Verifies that occurred some validation error. */
		if(anotherEntity != null){
			crudException = addValidationError(crudException, "", "number", "manageSchoolRooms.form.validation");
			throw crudException;
		}
	}
	
	/** Validates business rules in update the entity. */
	@Override
	public void validateUpdate(SchoolRoom entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;		
		SchoolRoom anotherEntity = null;
		
		/** Rule 1: Cannot have duplication in register of schoolRooms. */
		try {
			anotherEntity = schoolRoomDAO.checksDuplicity(entity);
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
				
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "number", "manageSchoolRooms.form.validation");
				throw crudException;
			}
		}		
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(SchoolRoom schoolRoom) {		
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the schoolRoom has some assignment. */
		Assignment assignmentEntity = null;
		
		try {
			assignmentEntity = assignmentDAO.retrieveBySchoolRoomId(schoolRoom);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "manageSchoolRooms.list.validationDelete.assignment";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(assignmentEntity != null) {
			errorMessageKey = "manageSchoolRooms.list.validationDelete.assignment";
			return errorMessageKey;
		}
		
		return errorMessageKey;
	}
	
	/** Method used to verify that a student was removed from schoolRoom and has AssignmentGroup. */
	public String validateExclusionStudent(Student student, SchoolRoom schoolRoom){		
		/** Checks if the schoolRoom has some assignment. */
		List<Assignment> listAssignments = null;
		
		try {
			listAssignments = assignmentDAO.retrieveBySchoolRoom(schoolRoom);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	
		if(listAssignments != null){
			/** Checks if the assignment has some assignmentGroup. */
			List<AssignmentGroup> listAssignmentGroups = null;
			
			for(Assignment assigment : listAssignments){
				try {					
					listAssignmentGroups = assignmentGroupDAO.retrieveByAssignment(assigment);
					
					for(AssignmentGroup assignmentGroup : listAssignmentGroups){
						/** Returns the students of the assignmentGroup. */
						List<Student> studentsGroup = new ArrayList<Student>(assignmentGroup.getStudents());
						
						/** If the student is in the AssignmentGroup, returns an error message. */
						if(studentsGroup.contains(student)){
							return "manageSchoolRooms.form.students.validation";
						}
					}						
				}	
				catch (PersistentObjectNotFoundException e) {}
				catch (MultiplePersistentObjectsFoundException e) {}
			}
		}
		
		return "";
	}
	
	/** Returns list of all schoolRooms. */
	public List<SchoolRoom> getSchoolRooms(){
		return schoolRoomDAO.retrieveAll();
	}
	
	/** Method used to return a list of the schoolRooms of a period. */
	public List<SchoolRoom> getSchoolRooms(Period period){
		List<SchoolRoom> schoolRooms = null;
		
		try {
			schoolRooms = schoolRoomDAO.retrieveByPeriod(period);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		return schoolRooms;
	}
	
	/** Method used to return a list of the schoolRooms of a period and professor specified in the parameters. */
	public List<SchoolRoom> getSchoolRooms(Period period, Professor professor){
		List<SchoolRoom> schoolRooms = null;
		
		try {
			schoolRooms = schoolRoomDAO.retrieveByPeriodProfessor(period, professor);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		return schoolRooms;
	}
}