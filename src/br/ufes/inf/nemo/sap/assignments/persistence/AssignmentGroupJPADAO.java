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
 * Stateless session bean implementing a DAO for objects of the AssignmentGroup domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class AssignmentGroupJPADAO extends BaseJPADAO<AssignmentGroup> implements AssignmentGroupDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;

	/** Getter Domain Class. */
	@Override
	public Class<AssignmentGroup> getDomainClass() {
		return AssignmentGroup.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<AssignmentGroup> root) {
		/** Orders by assignment and number. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(AssignmentGroup_.assignment)));
		orderList.add(cb.asc(root.get(AssignmentGroup_.number)));
		return orderList;
	}
	
	/** Retrieves the assignment group that has the exact assignment and number specified in the parameter. */
	@Override
	public AssignmentGroup retrieveByNumber(Assignment assignment, String number) 
				throws 	PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the AssignmentGroup class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AssignmentGroup> cq = cb.createQuery(AssignmentGroup.class);
		Root<AssignmentGroup> root = cq.from(AssignmentGroup.class);

		/** Filters the query with the assignment and number. */
		cq.where(cb.and(cb.equal(root.get(AssignmentGroup_.assignment), assignment), 
						cb.equal(root.get(AssignmentGroup_.number), number)));
		AssignmentGroup result = executeSingleResultQuery(cq, assignment, number);
		return result;
	}
	
	/** Retrieves the list of assignment group that has the exact assignment specified in the parameter. */
	@Override
	public List<AssignmentGroup> retrieveByAssignment(Assignment assignment) 
				throws 	PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the AssignmentGroup class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AssignmentGroup> cq = cb.createQuery(AssignmentGroup.class);
		Root<AssignmentGroup> root = cq.from(AssignmentGroup.class);

		/** Filters the query with the assignment and number. */
		cq.where(cb.equal(root.get(AssignmentGroup_.assignment), assignment));
		List<AssignmentGroup> result = entityManager.createQuery(cq).getResultList();
		return result;
	}
}