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
 * Stateless session bean implementing a DAO for objects of the SchoolRoom domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class SchoolRoomJPADAO extends BaseJPADAO<SchoolRoom> implements SchoolRoomDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;
	
	/** Getter Domain Class. */
	@Override
	public Class<SchoolRoom> getDomainClass() {
		return SchoolRoom.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<SchoolRoom> root) {
		/** Orders by period, professor, course, discipline and number. */
		List<Order> orderList = new ArrayList<Order>();		
		orderList.add(cb.asc(root.get(SchoolRoom_.professor)));
		orderList.add(cb.desc(root.get(SchoolRoom_.period)));
		orderList.add(cb.asc(root.get(SchoolRoom_.course)));
		orderList.add(cb.asc(root.get(SchoolRoom_.discipline)));
		orderList.add(cb.asc(root.get(SchoolRoom_.number)));
		return orderList;
	}

	/** Retrieves the schoolRoom that has the exact professor specified in the parameter. */
	public SchoolRoom retrieveByProfessorId(Professor professor) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the professor. */
		cq.where(cb.equal(root.get(SchoolRoom_.professor), professor));
		SchoolRoom result = executeSingleResultQuery(cq, professor);
		return result;
	}
	
	/** Retrieves the schoolRoom that has the exact course specified in the parameter. */
	public SchoolRoom retrieveByCourseId(Course course) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the course. */
		cq.where(cb.equal(root.get(SchoolRoom_.course), course));
		SchoolRoom result = executeSingleResultQuery(cq, course);
		return result;
	}
	
	/** Retrieves the schoolRoom that has the exact discipline specified in the parameter. */
	public SchoolRoom retrieveByDisciplineId(Discipline discipline) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the discipline. */
		cq.where(cb.equal(root.get(SchoolRoom_.discipline), discipline));
		SchoolRoom result = executeSingleResultQuery(cq, discipline);
		return result;
	}
	
	/** Retrieves the schoolRoom that has the exact period specified in the parameter. */
	public SchoolRoom retrieveByPeriodId(Period period) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the period. */
		cq.where(cb.equal(root.get(SchoolRoom_.period), period));
		SchoolRoom result = executeSingleResultQuery(cq, period);
		return result;
	}
	
	/** Retrieves the list of schoolRooms that has the exact period specified in the parameter. */	
	public List<SchoolRoom> retrieveByPeriod(Period period)
								throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the period. */
		cq.where(cb.equal(root.get(SchoolRoom_.period), period));		
		List<SchoolRoom> result = entityManager.createQuery(cq).getResultList();
		return result;
	}
	
	/** Retrieves the list of schoolRoom that has the exact period and professor specified in the parameters. */	
	public List<SchoolRoom> retrieveByPeriodProfessor(Period period, Professor professor)
								throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the period. */
		cq.where(cb.and(			
							cb.equal(root.get(SchoolRoom_.period), period),
							cb.equal(root.get(SchoolRoom_.professor), professor)
								
						)
				);
		List<SchoolRoom> result = entityManager.createQuery(cq).getResultList();
		return result;
	}
	
	/** Verifies if already exists a schoolRoom with the same settings to avoid duplication. */
	public SchoolRoom checksDuplicity(SchoolRoom entity) 
							throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the SchoolRoom class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolRoom> cq = cb.createQuery(SchoolRoom.class);
		Root<SchoolRoom> root = cq.from(SchoolRoom.class);

		/** Filters the query with the period, couse, discipline and number. */
		cq.where(cb.and(	
							cb.equal(root.get(SchoolRoom_.period), entity.getPeriod()),
							cb.equal(root.get(SchoolRoom_.course), entity.getCourse()),
							cb.equal(root.get(SchoolRoom_.discipline), entity.getDiscipline()),
							cb.equal(root.get(SchoolRoom_.number), entity.getNumber())							
						)
				);
		SchoolRoom result = executeSingleResultQuery(cq);
		return result;
	}
}