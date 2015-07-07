package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Assignments" use case.
 * 
 * This use case consists of a CRUD for the class Assignment and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageAssignmentsService extends CrudService<Assignment> {
	/************************************************************************************************** 
	 * Method used to validates rules to delete the entity.
	 * 
	 * @param assignment
	 *      Assignment that will be deleted.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/
	public String validateExclusion(Assignment assignment);
	
	/************************************************************************************************** 
	 * Method used to return a list of all assignments.
	 * 
	 * @return 
	 * 		A list of all assignment objects.
	 ***************************************************************************************************/
	public List<Assignment> getAssignments();
	
	/************************************************************************************************** 
	 * Method used to return a list of the assignments of a schoolRoom.
	 * 
	 * @param schoolRoom
	 * 		The exact schoolRoom of the assignments to be retrieved.
	 * 
	 * @return 
	 * 		A list of assignments objects.
	 ***************************************************************************************************/
	public List<Assignment> getAssignmentsSchoolRoom(SchoolRoom schoolRoom);
}