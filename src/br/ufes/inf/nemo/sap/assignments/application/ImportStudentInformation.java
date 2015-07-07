package br.ufes.inf.nemo.sap.assignments.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;

/**
 * Local EJB interface for the session information component. This bean is responsible for storing information on each
 * different user of the system, such as the Spiritist object that represents the logged in user. It should also provide
 * an authentication method for the controller, identifying users of the system.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@Local
public interface ImportStudentInformation extends Serializable {	
	/**************************************************************************************************
	 * Method used to check if the student is already registered in the system.
	 * 
	 * @param enrollment
	 *      The exact enrollment of the student to be retrieved.
	 *      
	 * @return
	 * 		If exists, returns the student object, otherwise returns null. 
	 ***************************************************************************************************/
	public Student retrieveStudent(String enrollment);
	
	
	/**************************************************************************************************
	 * Method used to update the schoolRoom data in the database.
	 * 
	 * @param schoolRoom
	 *      New entity schoolRoom with the updated data.
	 * 
	 * @return
	 *		String with the validation error message key, if exists.
	 ***************************************************************************************************/	
	public String updateSchoolRoom(SchoolRoom schoolRoom);
	
	/**************************************************************************************************
	 * Method used to save the student data in the database.
	 * 
	 * @param student
	 *      New entity student with the new data.
	 * 
	 * @return
	 *		String with the validation error message key, if exists.
	 ***************************************************************************************************/	
	public Student saveStudent(Student student);
}
