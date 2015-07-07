package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.*;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the AssignmentGroup domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface AssignmentGroupDAO extends BaseDAO<AssignmentGroup> {
	/**
	 * Retrieves the assignment group that has the exact assignment and number specified in the parameter.
	 * 
	 * @param assignment
	 * 		The exact assignment of the assignment group to be retrieved.
	 *          
	 * @param number
	 *      The exact number of the assignment group to be retrieved.
	 * 
	 * @return 
	 * 		An AssignmentGroup object that matches the query.
	 */
	public AssignmentGroup retrieveByNumber(Assignment assignment, String number) 
				throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/** Retrieves the list of assignment group that has the exact assignment specified in the parameter. */	
	public List<AssignmentGroup> retrieveByAssignment(Assignment assignment) 
			throws 	PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}