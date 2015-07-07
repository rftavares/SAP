package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Students" use case.
 * 
 * This use case consists of a CRUD for the class Student and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageStudentsService extends CrudService<Student> {
	/************************************************************************************************** 
	 * Method used to validates rules to delete the entity.
	 * 
	 * @param student
	 *      Student that will be deleted.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/	
	public String validateExclusion(Student student);
	
	/************************************************************************************************** 
	 * Method used to return a list of all students.
	 * 
	 * @return 
	 * 		A list of all students objects.
	 ***************************************************************************************************/	
	public List<Student> getStudents();
}