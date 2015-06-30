package br.ufes.inf.nemo.sap.assignments.persistence;


import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.Course;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the Course domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface CourseDAO extends BaseDAO<Course> {
	/**
	 * Retrieves the course that has the exact code specified in the parameter.
	 * 
	 * @param code
	 * 		The exact code of the course to be retrieved.
	 * 
	 * @return 
	 * 		A Course object that matches the query.
	 */
	public Course retrieveByCode(String code) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}