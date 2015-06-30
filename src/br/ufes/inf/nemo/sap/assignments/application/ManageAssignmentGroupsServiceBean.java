package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Assignment Groups" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageAssignmentGroupsServiceBean 	extends CrudServiceBean<AssignmentGroup>
												implements ManageAssignmentGroupsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for AssignmentGroup objects. */
	@EJB 
	private AssignmentGroupDAO assignmentGroupDAO;
	
	/** Getter DAO for AssignmentGroup objects. */
	@Override
	public BaseDAO<AssignmentGroup> getDAO() {
		return assignmentGroupDAO;
	}

	/** Creates a new entity AssignmentGroup. */	
	@Override
	protected AssignmentGroup createNewEntity() {
		return new AssignmentGroup();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(AssignmentGroup entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
			
		/** Rule 1: Cannot have two assignment groups with the same assignment and number. */
		try {
			AssignmentGroup anotherEntity = assignmentGroupDAO.retrieveByNumber(entity.getAssignment(), entity.getNumber());			
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {
				crudException = addValidationError(crudException, "", "number", "manageAssignmentGroups.form.number.validation");
				throw crudException;
			}
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules in updating the entity. */
	@Override
	public void validateUpdate(AssignmentGroup entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		AssignmentGroup anotherEntity = null;
			
		/** Rule 1: Cannot have two assignment groups with the same assignment and number. */
		try {
			anotherEntity = assignmentGroupDAO.retrieveByNumber(entity.getAssignment(), entity.getNumber());
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "number", "manageAssignmentGroups.form.number.validation");
				throw crudException;
			}
		}
	}
	
	/** Returns list of all assignmentGroups. */
	public List<AssignmentGroup> getAssignmentGroups(){
		return assignmentGroupDAO.retrieveAll();
	}
}