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
 * Stateless session bean implementing a DAO for objects of the Period domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class PeriodJPADAO extends BaseJPADAO<Period> implements PeriodDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;

	/** Getter Domain Class. */
	@Override
	public Class<Period> getDomainClass() {
		return Period.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Period> root) {
		/** Orders by year and number. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.desc(root.get(Period_.year)));
		orderList.add(cb.desc(root.get(Period_.number)));
		return orderList;
	}
	
	/** Retrieves the period that has the exact year and number specified in the parameter. */
	@Override
	public Period retrieveByYearNumber(String year, String number)	
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Period class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Period> cq = cb.createQuery(Period.class);
		Root<Period> root = cq.from(Period.class);

		/** Filters the query with the year and number. */
		cq.where(cb.and(
						cb.equal(root.get(Period_.year), year), 
						cb.equal(root.get(Period_.number), number)
						)
				);
		Period result = executeSingleResultQuery(cq, year, number);
		return result;
	}
}