package br.ufes.inf.nemo.sap.lab.application;

import java.util.*;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.application.SAPInformation;
import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.sap.lab.domain.persistence.SupervisionDAO;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Supervisions" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageSupervisionsServiceBean 	extends CrudServiceBean<Supervision>
											implements ManageSupervisionsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for Supervision objects. */
	@EJB
	private SupervisionDAO supervisionDAO;

	/** Information on the whole application. */
	@EJB
	private SAPInformation sapInformation;
	
	/** Getter DAO for Supervision objects. */
	@Override
	public BaseDAO<Supervision> getDAO() {
		return supervisionDAO;
	}

	/** Creates a new entity Supervision. */
	@Override
	protected Supervision createNewEntity() {
		return new Supervision();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Supervision entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;

		try {
			/** Rule 1: The start date must be less than or equal to end date. */
			if(entity.getStartDate().after(entity.getEndDate())) {
				/** Occurred some validation error. */
				crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.startDate.validation");
				throw crudException;
			}
			
			/** Rule 2: Checks if the date range is valid. */
			double diffdays = sapInformation.calculateDifferenceDays(entity.getStartDate(), entity.getEndDate());
			
			switch(entity.getType()){
				case INITIATIONSCIENTIFIC:
					if(diffdays > 365) {	/** 1 year. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.initiationscientific.validation");
						throw crudException;
					}
					break;
					
				case GRADUATION:
					if(diffdays > 365) {	/** 1 year. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.graduation.validation");
						throw crudException;
					}
					break;
					
				case MASTER:
					if(diffdays > 1275) {	/** 3 years and 6 months. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.master.validation");
						throw crudException;
					}
					break;
					
				case DOCTORATE:
					if(diffdays > 2005) {	/** 5 years and 6 months. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.doctorate.validation");
						throw crudException;
					}
					break;
					
				default:
					break;	
			}
			
			/** Rule 3: The adivisor and coadivisor can not be equal. */
			if(entity.getCoadvisor() != null) {
				if(entity.getAdvisor().getId() == entity.getCoadvisor().getId()) {
					/** Occurred some validation error. */
					crudException = addValidationError(crudException, "", "coadvisor", "manageSupervisions.form.coadvisor.validation");
					throw crudException;
				}				
			}
			
			/** Rule 4: Cannot have two supervisions with the same theme. */
			Supervision anotherEntity = supervisionDAO.retrieveByTheme(entity.getTheme());
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {				
				crudException = addValidationError(crudException, "", "theme", "manageSupervisions.form.theme.validation");
				throw crudException;
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules to update the entity. */
	@Override
	public void validateUpdate(Supervision entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Supervision anotherEntity = null;

		try {
			/** Rule 1: The start date must be less than or equal to end date. */
			if(entity.getStartDate().after(entity.getEndDate())) {
				/** Occurred some validation error. */
				crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.startDate.validation");
				throw crudException;
			}
			
			/** Rule 2: Checks if the date range is valid. */
			double diffdays = sapInformation.calculateDifferenceDays(entity.getStartDate(), entity.getEndDate());
			
			switch(entity.getType()){
				case INITIATIONSCIENTIFIC:
					if(diffdays > 365) {	/** 1 year. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.initiationscientific.validation");
						throw crudException;
					}
					break;
					
				case GRADUATION:
					if(diffdays > 365) {	/** 1 year. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.graduation.validation");
						throw crudException;
					}
					break;
					
				case MASTER:
					if(diffdays > 1275) {	/** 3 years and 6 months. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.master.validation");
						throw crudException;
					}
					break;
					
				case DOCTORATE:	
					if(diffdays > 2005) {	/** 5 years and 6 months. */
						/** Occurred some validation error. */
						crudException = addValidationError(crudException, "", "startDate", "manageSupervisions.form.type.doctorate.validation");
						throw crudException;
					}
					break;
					
				default:
					break;
			}
			
			/** Rule 3: The adivisor and coadivisor can not be equal. */
			if(entity.getCoadvisor() != null) {
				if(entity.getAdvisor().getId() == entity.getCoadvisor().getId()) {
					/** Occurred some validation error. */
					crudException = addValidationError(crudException, "", "coadvisor", "manageSupervisions.form.coadvisor.validation");
					throw crudException;
				}
			}
			
			/** Rule 4: Cannot have two supervisions with the same theme. */
			anotherEntity = supervisionDAO.retrieveByTheme(entity.getTheme());
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if(anotherEntity != null){
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "theme", "manageSupervisions.form.theme.validation");
				throw crudException;
			}
		}
	}
	
	/** Returns list of all supervisions. */
	public List<Supervision> getSupervisions(){
		return supervisionDAO.retrieveAll();
	}
}