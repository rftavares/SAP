package br.ufes.inf.nemo.sap.lab.domain.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.lab.domain.ResearchGroup;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Interface for a DAO for objects of the ResearchGroup domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ResearchGroupDAO extends BaseDAO<ResearchGroup> {
	/**
	 * Retrieves the research group that has the exact name specified in the parameter.
	 * 
	 * @param name
	 *      The exact name of the research group to be retrieved.
	 * 
	 * @return 
	 * 		An ResearchGroup object that matches the query.
	 */
	public ResearchGroup retrieveByName(String name) 
								throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}