package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the Assignment domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface AssignmentDAO extends BaseDAO<Assignment> {
	/**
	 * Retrieves the list of assignments of the schoolRoom.
	 * 
	 * @param schoolRoom
	 *      The exact schoolRoom of the assignments to be retrieved.
	 * 
	 * @return 
	 * 		The list of assignments.
	 */
	public List<Assignment> retrieveBySchoolRoom(SchoolRoom schoolRoom)
								throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the assignment that has the exact schoolRoom specified in the parameter.
	 * 
	 * @param schoolRoom
	 *      The exact schoolRoom of the assignment to be retrieved.
	 * 
	 * @return 
	 * 		An Assignment object that matches the query.
	 */
	public Assignment retrieveBySchoolRoomId(SchoolRoom schoolRoom)
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Verifies if already exists an assignment with the same settings to avoid duplication.
	 * 
	 * @param entity
	 *      Assignment that will be verified duplicity.
	 * 
	 * @return 
	 * 		Returns the assignment of duplicity, otherwise returns null.
	 */
	public Assignment checksDuplicity(Assignment entity)
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}