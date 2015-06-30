package br.ufes.inf.nemo.sap.assignments.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.Student;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the Student domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface StudentDAO extends BaseDAO<Student> {
	/**
	 * Retrieves the institution that has the exact name specified in the parameter.
	 * 
	 * @param name
	 *          The exact name of the student to be retrieved.
	 * 
	 * @return An Student object that matches the query.
	 */
	public Student retrieveByName(String name) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the institution that has the exact email specified in the parameter.
	 * 
	 * @param email
	 *          The exact email of the student to be retrieved.
	 * 
	 * @return An Student object that matches the query.
	 */
	public Student retrieveByEmail(String email) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the institution that has the exact enrollment specified in the parameter.
	 * 
	 * @param enrollment
	 *          The exact enrollment of the student to be retrieved.
	 * 
	 * @return An Student object that matches the query.
	 */
	public Student retrieveByEnrollment(String enrollment) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}