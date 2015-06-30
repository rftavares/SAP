package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Interface for a DAO for objects of the SchoolRoom domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface SchoolRoomDAO extends BaseDAO<SchoolRoom> {
	/**
	 * Retrieves the schoolRoom that has the exact professor specified in the parameter.
	 * 
	 * @param professor
	 *      The exact professor of the schoolRoom to be retrieved.
	 * 
	 * @return 
	 * 		A SchoolRoom object that matches the query.
	 */
	public SchoolRoom retrieveByProfessorId(Professor professor) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the schoolRoom that has the exact course specified in the parameter.
	 * 
	 * @param course
	 *      The exact course of the schoolRoom to be retrieved.
	 * 
	 * @return 
	 * 		A SchoolRoom object that matches the query.
	 */
	public SchoolRoom retrieveByCourseId(Course course) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the schoolRoom that has the exact discipline specified in the parameter.
	 * 
	 * @param discipline
	 *      The exact discipline of the schoolRoom to be retrieved.
	 * 
	 * @return 
	 * 		A SchoolRoom object that matches the query.
	 */
	public SchoolRoom retrieveByDisciplineId(Discipline discipline) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the schoolRoom that has the exact period specified in the parameter.
	 * 
	 * @param period
	 *      The exact period of the schoolRoom to be retrieved.
	 * 
	 * @return 
	 * 		A SchoolRoom object that matches the query.
	 */
	public SchoolRoom retrieveByPeriodId(Period period) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the list of schoolRooms that has the exact period specified in the parameter.
	 * 
	 * @param period
	 *      The exact period of the schoolRoom to be retrieved.
	 * 
	 * @return 
	 * 		A SchoolRoom object that matches the query.
	 */
	public List<SchoolRoom> retrieveByPeriod(Period period) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Retrieves the list of schoolRoom that has the exact period and professor specified in the parameters.
	 * 
	 * @param period
	 *      The exact period of the schoolRoom to be retrieved.
	 * 
	 * @return 
	 * 		A SchoolRoom object that matches the query.
	 */
	public List<SchoolRoom> retrieveByPeriodProfessor(Period period, Professor professor) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
	
	/**
	 * Verifies if already exists a schoolRoom with the same settings to avoid duplication.
	 * 
	 * @param entity
	 *      SchoolRoom that will be verified duplicity.
	 * 
	 * @return 
	 * 		Returns the schoolRoom of duplicity, otherwise returns null.
	 */
	public SchoolRoom checksDuplicity(SchoolRoom entity) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}