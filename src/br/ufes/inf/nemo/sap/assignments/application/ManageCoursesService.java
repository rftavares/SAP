package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Courses" use case.
 * 
 * This use case consists of a CRUD for the class Course and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageCoursesService extends CrudService<Course> {	
	/************************************************************************************************** 
	 * Method used to validates rules to delete the entity.
	 * 
	 * @param course
	 *      Course that will be deleted.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/
	public String validateExclusion(Course course);
	
	/************************************************************************************************** 
	 * Method used to return a list of all courses.
	 * 
	 * @return 
	 * 		A list of all courses objects.
	 ***************************************************************************************************/
	public List<Course> getCourses();
}