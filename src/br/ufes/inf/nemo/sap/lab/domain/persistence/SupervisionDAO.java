package br.ufes.inf.nemo.sap.lab.domain.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the Supervision domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface SupervisionDAO extends BaseDAO<Supervision> {
	/**
	 * Retrieves the supervision that has the exact theme specified in the parameter.
	 * 
	 * @param theme
	 *          The exact theme of the supervision to be retrieved.
	 * 
	 * @return 	A Supervision object that matches the query.
	 */
	public Supervision retrieveByTheme(String theme) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the supervision that has the exact professor specified in the parameter.
	 * 
	 * @param professor
	 *          The exact professor of the supervision to be retrieved.
	 * 
	 * @return 	A Supervision object that matches the query.
	 */
	public Supervision retrieveByProfessorId(Professor professor) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the supervision that has the exact student specified in the parameter.
	 * 
	 * @param student
	 *          The exact student of the supervision to be retrieved.
	 * 
	 * @return 	A Supervision object that matches the query.
	 */
	public Supervision retrieveByStudentId(Student student) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}