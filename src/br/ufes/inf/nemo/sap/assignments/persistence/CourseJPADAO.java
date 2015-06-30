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
 * Stateless session bean implementing a DAO for objects of the Course domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class CourseJPADAO extends BaseJPADAO<Course> implements CourseDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;

	/** Getter Domain Class. */
	@Override
	public Class<Course> getDomainClass() {
		return Course.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Course> root) {
		/** Orders by name. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Course_.name)));
		return orderList;
	}
	
	/** Retrieves the course that has the exact code specified in the parameter. */
	@Override
	public Course retrieveByCode(String code) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Course class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> root = cq.from(Course.class);

		/** Filters the query with the code. */
		cq.where(cb.equal(root.get(Course_.code), code));
		Course result = executeSingleResultQuery(cq, code);		
		return result;
	}
}