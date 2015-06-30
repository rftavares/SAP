package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Disciplines" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageDisciplinesServiceBean 	extends CrudServiceBean<Discipline>
											implements ManageDisciplinesService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for Discipline objects. */
	@EJB 
	private DisciplineDAO disciplineDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;
	
	/** Getter DAO for Discipline objects. */
	@Override
	public BaseDAO<Discipline> getDAO() {
		return disciplineDAO;
	}

	/** Creates a new entity Discipline. */
	@Override
	protected Discipline createNewEntity() {
		return new Discipline();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Discipline entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;

		/** Rule 1: Cannot have two disciplines with the same code. */
		try {			
			Discipline anotherEntity = disciplineDAO.retrieveByCode(entity.getCode());
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {				
				crudException = addValidationError(crudException, "", "code", "manageDisciplines.form.code.validation");				
				throw crudException;
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(Discipline entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Discipline anotherEntity = null;

		/** Rule 1: Cannot have two disciplines with the same code. */
		try {			
			anotherEntity = disciplineDAO.retrieveByCode(entity.getCode());
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null){
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "code", "manageDisciplines.form.code.validation");
				throw crudException;
			}
		}
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(Discipline discipline) {		
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the discipline has some schoolRoom. */
		SchoolRoom schoolRoomEntity = null;
		
		try {
			schoolRoomEntity = schoolRoomDAO.retrieveByDisciplineId(discipline);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "manageDisciplines.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(schoolRoomEntity != null) {
			errorMessageKey = "manageDisciplines.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		return errorMessageKey;
	}
	
	/** Returns list of all disciplines. */
	public List<Discipline> getDisciplines(){
		return disciplineDAO.retrieveAll();
	}
}