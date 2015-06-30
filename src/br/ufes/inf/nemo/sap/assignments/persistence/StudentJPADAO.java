package br.ufes.inf.nemo.sap.assignments.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the Student domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class StudentJPADAO extends BaseJPADAO<Student> implements StudentDAO{
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;
	
	/** Getter Domain Class. */
	@Override
	public Class<Student> getDomainClass() {
		return Student.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Student> root) {
		/** Orders by name. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Student_.name)));
		return orderList;
	}
	
	/** Retrieves the student that has the exact name specified in the parameter. */
	@Override
	public Student retrieveByName(String name) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Student class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);

		/** Filters the query with the name. */
		cq.where(cb.equal(root.get(Student_.name), name));
		Student result = executeSingleResultQuery(cq, name);		
		return result;
	}
	
	/** Retrieves the student that has the exact email specified in the parameter. */
	@Override
	public Student retrieveByEmail(String email) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Student class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);

		/** Filters the query with the email. */
		cq.where(cb.equal(root.get(Student_.email), email));
		Student result = executeSingleResultQuery(cq, email);		
		return result;
	}
	
	/** Retrieves the student that has the exact enrollment specified in the parameter. */
	@Override
	public Student retrieveByEnrollment(String enrollment) 
						throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the Student class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);

		/** Filters the query with the enrollment. */
		cq.where(cb.equal(root.get(Student_.enrollment), enrollment));
		Student result = executeSingleResultQuery(cq, enrollment);
		return result;
	}
}