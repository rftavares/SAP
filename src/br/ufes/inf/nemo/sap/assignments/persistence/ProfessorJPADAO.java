package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the Professor domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ProfessorJPADAO extends BaseJPADAO<Professor> implements ProfessorDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;

	/** Getter Domain Class. */
	@Override
	public Class<Professor> getDomainClass() {
		return Professor.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Professor> root) {
		/** Orders by name. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Professor_.name)));
		return orderList;
	}

	/** Retrieves the professor that has the exact name specified in the parameter. */
	@Override
	public Professor retrieveByName(String name) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException{
		/** Constructs the query over the Professor class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Professor> cq = cb.createQuery(Professor.class);
		Root<Professor> root = cq.from(Professor.class);

		/** Filters the query with the name. */
		cq.where(cb.equal(root.get(Professor_.name), name));
		Professor result = executeSingleResultQuery(cq, name);
		return result;
	}
	
	/** Retrieves the professor that has the exact email specified in the parameter. */
	@Override
	public Professor retrieveByEmail(String email) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException{
		/** Constructs the query over the Professor class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Professor> cq = cb.createQuery(Professor.class);
		Root<Professor> root = cq.from(Professor.class);

		/** Filters the query with the email. */
		cq.where(cb.equal(root.get(Professor_.email), email));
		Professor result = executeSingleResultQuery(cq, email);
		return result;
	}
	
	/** The new professor entity updated. */
	public Professor updateProfessor(Professor entity) {		
		return entityManager.merge(entity);
	}
}