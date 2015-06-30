package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Periods" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManagePeriodsServiceBean 	extends CrudServiceBean<Period> 
										implements ManagePeriodsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for Period objects. */
	@EJB
	private PeriodDAO periodDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;

	/** Getter DAO for Period objects. */
	@Override
	public BaseDAO<Period> getDAO() {
		return periodDAO;
	}

	/** Creates a new entity Period. */
	@Override
	protected Period createNewEntity() {
		return new Period();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(Period entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		
		try {
			/** Rule 1: The start date must be less than or equal to end date. */
			if(entity.getStartDate().after(entity.getEndDate())) {
				/** Occurred some validation error. */
				crudException = addValidationError(crudException, "", "startDate", "managePeriods.form.startDate.validation");
				throw crudException;
			}
			
			/** Rule 2: Cannot have two periods with the same year and number. */
			Period anotherEntity = periodDAO.retrieveByYearNumber(entity.getYear(), entity.getNumber());			
			
			/** Verifies that occurred some validation error. */
			if (anotherEntity != null) {
				crudException = addValidationError(crudException, "", "number", "managePeriods.form.number.validation");
				throw crudException;
			}
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules in update the entity. */
	@Override
	public void validateUpdate(Period entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		Period anotherEntity = null;
		
		try {
			/** Rule 1: The start date must be less than or equal to end date. */
			if(entity.getStartDate().after(entity.getEndDate())) {
				/** Occurred some validation error. */
				crudException = addValidationError(crudException, "", "startDate", "managePeriods.form.startDate.validation");
				throw crudException;
			}
			
			/** Rule 2: Cannot have two periods with the same year and number. */
			anotherEntity = periodDAO.retrieveByYearNumber(entity.getYear(), entity.getNumber());
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "number", "managePeriods.form.number.validation");
				throw crudException;
			}
		}
		
	}
	
	/** Validates business rules to delete the entity. */
	public String validateExclusion(Period period) {		
		/** Variable used to store the error message. */
		String errorMessageKey = "";
		
		/** Rule 1: Checks if the period has some schoolRoom. */
		SchoolRoom schoolRoomEntity = null;
		
		try {
			schoolRoomEntity = schoolRoomDAO.retrieveByPeriodId(period);
		}	
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {
			/** Occurred some validation error. */
			errorMessageKey = "managePeriods.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		/** Verifies that occurred some validation error. */
		if(schoolRoomEntity != null) {
			errorMessageKey = "managePeriods.list.validationDelete.schoolRoom";
			return errorMessageKey;
		}
		
		return errorMessageKey;
	}
	
	/** Returns list of all periods. */
	public List<Period> getPeriods(){
		return periodDAO.retrieveAll();
	}
}