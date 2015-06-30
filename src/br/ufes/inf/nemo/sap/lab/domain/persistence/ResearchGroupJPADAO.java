package br.ufes.inf.nemo.sap.lab.domain.persistence;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;

import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing a DAO for objects of the ResearchGroup domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) 
 * have to be implemented in this class.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ResearchGroupJPADAO extends BaseJPADAO<ResearchGroup> implements ResearchGroupDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext 
	private EntityManager entityManager;
	
	/** Getter Domain Class. */
	@Override
	public Class<ResearchGroup> getDomainClass() {
		return ResearchGroup.class;
	}

	/** Getter EntityManager. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** Responsible for ordering the data table in the apresentation layer. */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<ResearchGroup> root) {
		/** Orders by name. */
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(ResearchGroup_.name)));
		return orderList;
	}
	
	/** Retrieves the research group that has the exact name specified in the parameter. */
	@Override
	public ResearchGroup retrieveByName(String name) 
								throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Constructs the query over the ResearchGroup class. */
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ResearchGroup> cq = cb.createQuery(ResearchGroup.class);
		Root<ResearchGroup> root = cq.from(ResearchGroup.class);

		/** Filters the query with the name. */
		cq.where(cb.equal(root.get(ResearchGroup_.name), name));
		ResearchGroup result = executeSingleResultQuery(cq, name);		
		return result;
	}
}