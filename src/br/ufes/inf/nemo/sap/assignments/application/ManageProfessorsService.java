package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.Professor;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Professors" use case.
 * 
 * This use case consists of a CRUD for the class Professor and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageProfessorsService extends CrudService<Professor> {
	/************************************************************************************************** 
	 * Method used to validates rules to delete the entity.
	 * 
	 * @param professor
	 *      Professor that will be deleted.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/
	public String validateExclusion(Professor professor);
	
	/************************************************************************************************** 
	 * Method used to return a list of all professors.
	 * 
	 * @return 
	 * 		A list of all professors objects.
	 ***************************************************************************************************/
	public List<Professor> getProfessors();
}