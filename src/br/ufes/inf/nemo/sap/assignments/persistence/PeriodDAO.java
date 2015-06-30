package br.ufes.inf.nemo.sap.assignments.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.Period;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Interface for a DAO for objects of the Period domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface PeriodDAO extends BaseDAO<Period> {
	/**
	 * Retrieves the period that has the exact year and number specified in the parameter.
	 * 
	 * @param year
	 * 		The exact year of the period to be retrieved.
	 *          
	 * @param number
	 *      The exact number of the period to be retrieved.
	 * 
	 * @return 
	 * 		An Period object that matches the query.
	 */
	public Period retrieveByYearNumber(String year, String number) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}