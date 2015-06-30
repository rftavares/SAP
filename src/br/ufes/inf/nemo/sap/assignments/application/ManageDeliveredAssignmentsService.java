package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.DeliveredAssignment;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Delivered Assignments" use case.
 * 
 * This use case consists of a CRUD for the class DeliveredAssignment and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageDeliveredAssignmentsService extends CrudService<DeliveredAssignment> {
	/** Returns list of all deliveredAssignments. */
	List<DeliveredAssignment> getDeliveredAssignments();
}