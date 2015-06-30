package br.ufes.inf.nemo.sap.lab.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.lab.domain.ResearchGroup;
import br.ufes.inf.nemo.sap.lab.domain.persistence.ResearchGroupDAO;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Research Groups" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageResearchGroupsServiceBean 	extends CrudServiceBean<ResearchGroup> 
												implements ManageResearchGroupsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for ResearchGroup objects. */
	@EJB
	private ResearchGroupDAO researchGroupDAO;

	/** Getter DAO for ResearchGroup objects. */
	@Override
	public BaseDAO<ResearchGroup> getDAO() {
		return researchGroupDAO;
	}

	/** Creates a new entity ResearchGroup. */
	@Override
	protected ResearchGroup createNewEntity() {
		return new ResearchGroup();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(ResearchGroup entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;

		/** Rule 1: Cannot have two researchGroups with the same name. */
		try {			
			ResearchGroup anotherEntity = researchGroupDAO.retrieveByName(entity.getName());
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {				
				crudException = addValidationError(crudException, "", "name", "manageResearchGroups.form.name.validation");
				throw crudException;
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(ResearchGroup entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		ResearchGroup anotherEntity = null;

		/** Rule 1: Cannot have two researchGroups with the same name. */
		try {			
			anotherEntity = researchGroupDAO.retrieveByName(entity.getName());
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null){
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "name", "manageResearchGroups.form.name.validation");
				throw crudException;
			}
		}
	}
	
	/** Returns list of all researchGroups. */
	public List<ResearchGroup> getResearchGroups(){
		return researchGroupDAO.retrieveAll();
	}
}