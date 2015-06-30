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
public interface SessionInformation extends Serializable {
	/**************************************************************************************************
	 * Method used to returns the professor logged.
	 * 
	 * @return 
	 * 		The professor object logged.
	 ***************************************************************************************************/
	public Professor getCurrentProfessor();
	
	/**************************************************************************************************
	 * Method used to returns the student logged.
	 * 
	 * @return 
	 * 		The student object logged.
	 ***************************************************************************************************/
	public Student getCurrentStudent();
	
	/**************************************************************************************************
	 * Method used to authenticate professor.
	 * 
	 * @param email
	 *      Email informed in the professor's login page.
	 *      
	 * @param password
	 *      Password informed in the professor's login page.     
	 * 
	 * @return	 * 
	 * 		On success, returns the professor logged object, otherwise returns null.
	 ***************************************************************************************************/
	public Professor loginProfessor(String email, String password);
	
	/**************************************************************************************************
	 * Method used to authenticate student.
	 * 
	 * @param enrollment
	 *      Enrollment informed in the student's login page.
	 *      
	 * @param password
	 *      Password informed in the student's login page.
	 * 
	 * @return
	 * 		On success, returns the student logged object, otherwise returns null. 
	 ***************************************************************************************************/
	public Student loginStudent(String enrollment, String password);
	
	/**************************************************************************************************
	 * Method called through the data change page to update professor informations.
	 * 
	 * @param entity
	 *      New entity professor with the updated data.
	 * 
	 * @return
	 *		String with the validation error message key, if exists.
	 ***************************************************************************************************/	
	public String updateProfessor(Professor entity);
	
	/**************************************************************************************************
	 * Method called through the data change page to update student informations.
	 * 
	 * @param entity
	 *      New entity student with the updated data.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/	
	public String updateStudent(Student entity);
}
