package br.ufes.inf.nemo.sap.assignments.application;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateful session bean implementing the session information component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@SessionScoped
@Stateful
public class ImportStudentInformationBean implements ImportStudentInformation {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for Student objects. */
	@EJB
	private StudentDAO studentDAO;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;

	/** Method used to check if the student is already registered in the system. */
	public Student retrieveStudent(String enrollment){
		Student student = null;
		
		try{
			student = studentDAO.retrieveByEnrollment(enrollment);
		}
		catch (PersistentObjectNotFoundException e) {} 
		catch (MultiplePersistentObjectsFoundException e) {}
		
		return student;
	}
	
	/** Method used to update the schoolRoom data in the database. */
	public String updateSchoolRoom(SchoolRoom schoolRoom) {
		try{
			schoolRoomDAO.merge(schoolRoom);			
		}
		catch(Exception e){
			e.printStackTrace();
			return "importStudents.form.schoolRoom.database.error";
		}
		
		return "";
	}
	
	/** Method used to save the student data in the database. */
	public Student saveStudent(Student student) {
		try{
			studentDAO.save(student);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return student;
	}	
}