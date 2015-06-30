package br.ufes.inf.nemo.sap.assignments.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.Discipline;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Interface for a DAO for objects of the Discipline domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface DisciplineDAO extends BaseDAO<Discipline> {
	/**
	 * Retrieves the discipline that has the exact name specified in the parameter.
	 * 
	 * @param name
	 *          The exact name of the discipline to be retrieved.
	 * 
	 * @return An Discipline object that matches the query.
	 */
	public Discipline retrieveByName(String name) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the discipline that has the exact code specified in the parameter.
	 * 
	 * @param code
	 *          The exact code of the discipline to be retrieved.
	 * 
	 * @return An Discipline object that matches the query.
	 */
	public Discipline retrieveByCode(String code) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}