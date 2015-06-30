package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing a DAO for objects of the Assignment domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class AssignmentJPADAO extends BaseJPADAO<Assignment> implements AssignmentDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;

	/** Getter Domain Class. */
	@Override
	public Class<Assignment> getDomainClass() {
		return Assignment.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Assignment> root) {
		/** Orders by schoolRoom and subject. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Assignment_.schoolRoom)));
		orderList.add(cb.asc(root.get(Assignment_.number)));		
		return orderList;
	}
	
	/** Retrieves the list of assignments of the schoolRoom. */
	@Override
	public List<Assignment> retrieveBySchoolRoom(SchoolRoom schoolRoom) 
				throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Assignment class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
		Root<Assignment> root = cq.from(Assignment.class);

		/** Filters the query with the schoolRoom. */
		cq.where(cb.equal(root.get(Assignment_.schoolRoom), schoolRoom));
		List<Assignment> result = entityManager.createQuery(cq).getResultList();
		return result;
	}
	
	/** Retrieves the assignment that has the exact schoolRoom specified in the parameter. */
	@Override
	public Assignment retrieveBySchoolRoomId(SchoolRoom schoolRoom) 
				throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Assignment class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
		Root<Assignment> root = cq.from(Assignment.class);

		/** Filters the query with the schoolRoom. */
		cq.where(cb.equal(root.get(Assignment_.schoolRoom), schoolRoom));
		Assignment result = executeSingleResultQuery(cq, schoolRoom);
		return result;
	}
	
	/** Verifies if already exists an assignment with the same settings to avoid duplication. */
	public Assignment checksDuplicity(Assignment entity) 
				throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Assignment class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
		Root<Assignment> root = cq.from(Assignment.class);

		/** Filters the query with the schoolRoom and number. */
		cq.where(cb.and(	
							cb.equal(root.get(Assignment_.schoolRoom), entity.getSchoolRoom()),
							cb.equal(root.get(Assignment_.number), entity.getNumber())							
						)
				);
		Assignment result = executeSingleResultQuery(cq);
		return result;
	}
}