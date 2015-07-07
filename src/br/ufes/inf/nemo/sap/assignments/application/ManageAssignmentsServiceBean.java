package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Assignments" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageAssignmentsServiceBean 	extends CrudServiceBean<Assignment>
											implements ManageAssignmentsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for Assignment objects. */
	@EJB
	private AssignmentDAO assignmentDAO;
	
	/** The DAO for AssignmentGroup objects. */
	@EJB
	private AssignmentGroupDAO assignmentGroupDAO;

	/** Getter DAO for Assignment objects. */
	@Override
	public BaseDAO<Assignment> getDAO() {
		return assignmentDAO;
	}
	
	/** Creates a new entity Assignment. */
	@Override
	protected Assignment createNewEntity() {
		return new Assignment();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Assignment entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;		
		Assignment anotherEntity = null;
		
		/** Rule 1: Cannot have duplication in register of assignments. */
		try {
			anotherEntity = assignmentDAO.checksDuplicity(entity);
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		/** Verifies that occurred some validation error. */
		if(anotherEntity != null){
			crudException = addValidationError(crudException, "", "number", "manageAssignments.form.validation");
			throw crudException;
		}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(Assignment entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;		
		Assignment anotherEntity = null;
		
		/** Rule 1: Cannot have duplication in register of assignments. */
		try {
			anotherEntity = assignmentDAO.checksDuplicity(entity);
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "number", "manageAssignments.form.validation");
				throw crudException;
			}
		}
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(Assignment assignment) {		
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the assignment has some assignmentGroup. */
		List<AssignmentGroup> assignmentGroups = null;
		
		try {
			assignmentGroups = assignmentGroupDAO.retrieveByAssignment(assignment);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}		
		
		/** Verifies that occurred some validation error. */
		if(assignmentGroups.size() > 0) {
			errorMessageKey = "manageAssignments.list.validationDelete.assignmentGroup";
			return errorMessageKey;
		}
		
		return errorMessageKey;
	}
	
	/** Method used to return a list of all assignments. */
	public List<Assignment> getAssignmentsSchoolRoom(SchoolRoom schoolRoom) {
		List<Assignment> teste = null;
		
		try {
			teste = assignmentDAO.retrieveBySchoolRoom(schoolRoom);
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		return teste;
	}
	
	/** Method used to return a list of all assignments. */
	public List<Assignment> getAssignments(){
		return assignmentDAO.retrieveAll();
	}
}