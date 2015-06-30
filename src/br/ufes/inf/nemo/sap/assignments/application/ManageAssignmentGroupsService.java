package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Assignment Groups" use case.
 * 
 * This use case consists of a CRUD for the class AssignmentGroup and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageAssignmentGroupsService extends CrudService<AssignmentGroup> {
	/************************************************************************************************** 
	 * Method used to return a list of all assignmentGroups.
	 * 
	 * @return 
	 * 		A list of all assignmentGroup objects.
	 ***************************************************************************************************/	
	public List<AssignmentGroup> getAssignmentGroups();
}