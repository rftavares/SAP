package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Schoolrooms" use case.
 * 
 * This use case consists of a CRUD for the class SchoolRoom and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageSchoolRoomsService extends CrudService<SchoolRoom> {
	/************************************************************************************************** 
	 * Method used to validates rules to delete the entity.
	 * 
	 * @param schoolRoom
	 *      SchoolRoom that will be deleted.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/
	public String validateExclusion(SchoolRoom schoolRoom);
	
	/************************************************************************************************** 
	 * Method used to return a list of all schoolRooms.
	 * 
	 * @return 
	 * 		A list of all schoolRooms objects.
	 ***************************************************************************************************/
	public List<SchoolRoom> getSchoolRooms();
	
	/************************************************************************************************** 
	 * Method used to return a list of the schoolRooms of a period.
	 * 
	 * @return
	 * 		A SchoolRooms object that matches the query.
	 ***************************************************************************************************/
	public List<SchoolRoom> getSchoolRooms(Period period);
	
	/************************************************************************************************** 
	 * Method used to return a list of the schoolRooms of a period and professor specified in the parameters.
	 * 
	 * @return 
	 * 		A SchoolRooms object that matches the query.
	 ***************************************************************************************************/
	public List<SchoolRoom> getSchoolRooms(Period period, Professor professor);
	
	/************************************************************************************************** 
	 * Method used to verify that a student was removed from schoolRoom and has AssignmentGroup.
	 * 
	 * @param student
	 *      Student will be removed from schoolRoom.
	 *      
	 * @param schoolRoom
	 *      SchoolRoom that the student is enrolled.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/
	public String validateExclusionStudent(Student student, SchoolRoom schoolRoom);
}