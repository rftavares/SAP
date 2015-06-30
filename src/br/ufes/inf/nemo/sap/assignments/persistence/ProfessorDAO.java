package br.ufes.inf.nemo.sap.assignments.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.Professor;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the Professor domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ProfessorDAO extends BaseDAO<Professor> {
	/**
	 * Retrieves the professor that has the exact name specified in the parameter.
	 * 
	 * @param name
	 *          The exact name of the professor to be retrieved.
	 * 
	 * @return An Professor object that matches the query.
	 */
	public Professor retrieveByName(String name) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the professor that has the exact email specified in the parameter.
	 * 
	 * @param email
	 *          The exact email of the professor to be retrieved.
	 * 
	 * @return An Professor object that matches the query.
	 */
	public Professor retrieveByEmail(String email) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
		
	/**
	 * Updates professor in the database.
	 * 
	 * @param entity
	 *      Professor entity with new data that will be updated.
	 * 
	 * @return 
	 * 		The new professor entity updated.
	 */
	public Professor updateProfessor(Professor entity);
}