package br.ufes.inf.nemo.sap.assignments.application;

import java.util.*;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.sap.lab.domain.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Professors" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageProfessorsServiceBean 	extends CrudServiceBean<Professor>
											implements ManageProfessorsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Information on the whole application. */
	@EJB
	private SAPInformation sapInformation;
	
	/** The DAO for ResearchGroup objects. */
	@EJB
	private ResearchGroupDAO researchGroupDAO;
	
	/** The DAO for Professor objects. */
	@EJB
	private ProfessorDAO professorDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SupervisionDAO supervisionDAO;
		
	/** Getter DAO for Professor objects. */
	@Override
	public BaseDAO<Professor> getDAO() {
		return professorDAO;
	}

	/** Creates a new entity Professor. */
	@Override
	protected Professor createNewEntity() {
		return new Professor();
	}
		
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Professor entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;

		try {
			/** Rule 1: Cannot have new professor without password. */
			if(entity.getPassword().isEmpty()) {
				/** Occurred some validation error. */
				crudException = addValidationError(crudException, "", "password", "manageProfessors.form.password.validation");
				throw crudException;
			}
			else{
				/** Creates the MD5 hash for the password. */
				String md5pwd = sapInformation.transformStringToMd5Hash(entity.getPassword());
				
				/** Updates the value of the password. */
				entity.setPassword(md5pwd);
			}
						
			/** Rule 2: Cannot have two professors with the same email. */
			Professor anotherEntity = professorDAO.retrieveByEmail(entity.getEmail());
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {
				crudException = addValidationError(crudException, "", "email", "manageProfessors.form.email.validation");
				throw crudException;
			}
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(Professor entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Professor anotherEntity = null;
		
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
				anotherEntity = professorDAO.retrieveById(entity.getId());
				entity.setPassword(anotherEntity.getPassword());
			}
			
			/** Rule 1: Cannot have two professors with the same email. */			
			anotherEntity = professorDAO.retrieveByEmail(entity.getEmail());
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if(anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "email", "manageProfessors.form.email.validation");
				throw crudException;
			}
		}
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(Professor professor) {		
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the professor has some schoolRoom. */
		SchoolRoom schoolRoomEntity = null;
		
		try {
			schoolRoomEntity = schoolRoomDAO.retrieveByProfessorId(professor);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "manageProfessors.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(schoolRoomEntity != null) {
			errorMessageKey = "manageProfessors.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		/** Rule 2: Checks if the professor has some supervision. */
		Supervision supervisionEntity = null;
		
		try {
			supervisionEntity = supervisionDAO.retrieveByProfessorId(professor);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "manageProfessors.list.validationDelete.supervision";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(supervisionEntity != null) {
			errorMessageKey = "manageProfessors.list.validationDelete.supervision";
			return errorMessageKey;
		}
		
		/** Rule 3: Checks if the professor has some researchGroup. */
		List<ResearchGroup> researchGroups = researchGroupDAO.retrieveAll();
		
		/** Checks each researchGroup. */
		for(ResearchGroup item : researchGroups) {
			List<Professor> listProfessors = new ArrayList<Professor>(item.getProfessors());
			
			/** Checks if the professor is in list. */
			if(listProfessors.contains(professor)){
				/** Occurred some validation error. */
				errorMessageKey = "manageProfessors.list.validationDelete.researchGroup";
				return errorMessageKey;
			}
		}
		
		return errorMessageKey;
	}
	
	/** Returns list of all professors. */
	public List<Professor> getProfessors(){
		return professorDAO.retrieveAll();
	}
}