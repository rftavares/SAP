package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing a DAO for objects of the Discipline domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class DisciplineJPADAO extends BaseJPADAO<Discipline> implements DisciplineDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;	
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;
	
	/** Getter Domain Class. */
	@Override
	public Class<Discipline> getDomainClass() {
		return Discipline.class;
	}
	
	/** Getter EntityManager. */
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Discipline> root) {
		/** Orders by name. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Discipline_.name)));
		return orderList;
	}
	
	/** Retrieves the discipline that has the exact name specified in the parameter. */
	@Override
	public Discipline retrieveByName(String name) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Discipline class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Discipline> cq = cb.createQuery(Discipline.class);
		Root<Discipline> root = cq.from(Discipline.class);

		/** Filters the query with the name. */
		cq.where(cb.equal(root.get(Discipline_.name), name));
		Discipline result = executeSingleResultQuery(cq, name);		
		return result;
	}
	
	/** Retrieves the discipline that has the exact code specified in the parameter. */
	@Override
	public Discipline retrieveByCode(String code) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Discipline class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Discipline> cq = cb.createQuery(Discipline.class);
		Root<Discipline> root = cq.from(Discipline.class);

		/** Filters the query with the code. */
		cq.where(cb.equal(root.get(Discipline_.code), code));
		Discipline result = executeSingleResultQuery(cq, code);		
		return result;
	}
}