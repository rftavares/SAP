package br.ufes.inf.nemo.sap.lab.domain.persistence;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the Supervision domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class SupervisionJPADAO extends BaseJPADAO<Supervision> implements SupervisionDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;
	
	/** Getter Domain Class. */
	@Override
	public Class<Supervision> getDomainClass() {
		return Supervision.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Supervision> root) {
		/** Orders by type and theme. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Supervision_.type)));
		orderList.add(cb.asc(root.get(Supervision_.theme)));
		return orderList;
	}
	
	/** Retrieves the supervision that has the exact theme specified in the parameter. */
	@Override
	public Supervision retrieveByTheme(String theme) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Supervision class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Supervision> cq = cb.createQuery(Supervision.class);
		Root<Supervision> root = cq.from(Supervision.class);

		/** Filters the query with the theme. */
		cq.where(cb.equal(root.get(Supervision_.theme), theme));
		Supervision result = executeSingleResultQuery(cq, theme);		
		return result;
	}
	
	/** Retrieves the supervision that has the exact professor specified in the parameter. */
	public Supervision retrieveByProfessorId(Professor professor) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Supervision class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Supervision> cq = cb.createQuery(Supervision.class);
		Root<Supervision> root = cq.from(Supervision.class);

		/** Filters the query with the advisor or coadvisor. */
		cq.where(cb.or(
						cb.equal(root.get(Supervision_.advisor), professor),
						cb.equal(root.get(Supervision_.coadvisor), professor)
					  	)
				);
		Supervision result = executeSingleResultQuery(cq, professor);
		return result;
	}
	
	/** Retrieves the supervision that has the exact student specified in the parameter. */
	public Supervision retrieveByStudentId(Student student)	
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Supervision class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Supervision> cq = cb.createQuery(Supervision.class);
		Root<Supervision> root = cq.from(Supervision.class);

		/** Filters the query with the student. */
		cq.where(cb.equal(root.get(Supervision_.student), student));
		Supervision result = executeSingleResultQuery(cq, student);
		return result;
	}
}