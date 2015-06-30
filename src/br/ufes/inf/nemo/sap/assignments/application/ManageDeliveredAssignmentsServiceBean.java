package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.*;

import br.ufes.inf.nemo.sap.assignments.domain.DeliveredAssignment;
import br.ufes.inf.nemo.sap.assignments.persistence.DeliveredAssignmentDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

/**
 * Stateless session bean implementing the "Manage Delivered Assignments" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageDeliveredAssignmentsServiceBean 	extends CrudServiceBean<DeliveredAssignment> 
													implements ManageDeliveredAssignmentsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for DeliveredAssignment objects. */
	@EJB
	private DeliveredAssignmentDAO deliveredAssignmentDAO;

	/** Getter DAO for DeliveredAssignment objects. */
	@Override
	public BaseDAO<DeliveredAssignment> getDAO() {
		return deliveredAssignmentDAO;
	}

	/** Creates a new entity DeliveredAssignment. */
	@Override
	protected DeliveredAssignment createNewEntity() {
		return new DeliveredAssignment();
	}

	/** Returns list of all deliveredAssignments. */
	public List<DeliveredAssignment> getDeliveredAssignments(){
		return deliveredAssignmentDAO.retrieveAll();
	}
}
