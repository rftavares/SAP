package br.ufes.inf.nemo.sap.assignments.application;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.util.ejb3.application.*;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Stateless session bean implementing the "Manage Assignment Groups" use case component. 
 * 
 * See the implemented interface documentation for details.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Stateless
public class ManageAssignmentGroupsServiceBean 	extends CrudServiceBean<AssignmentGroup>
												implements ManageAssignmentGroupsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for AssignmentGroup objects. */
	@EJB 
	private AssignmentGroupDAO assignmentGroupDAO;
	
	/** Getter DAO for AssignmentGroup objects. */
	@Override
	public BaseDAO<AssignmentGroup> getDAO() {
		return assignmentGroupDAO;
	}

	/** Creates a new entity AssignmentGroup. */	
	@Override
	protected AssignmentGroup createNewEntity() {
		return new AssignmentGroup();
	}
	
	/** Validates business rules in creating the entity. */
	@Override
	public void validateCreate(AssignmentGroup entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		AssignmentGroup anotherEntity = null;
			
		/** Rule 1: Cannot have two assignment groups with the same assignment and number. */
		try {
			anotherEntity = assignmentGroupDAO.retrieveByNumber(entity.getAssignment(), entity.getNumber());
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "number", "manageAssignmentGroups.form.number.validation");
				throw crudException;
			}
		}
		
		/** Returns the registered students in the group */
		List<Student> studentsGroup = new ArrayList<Student>(entity.getStudents());
		
		/** Rule 2: Checks whether the assignmentGroup is without students. */
		if(studentsGroup.size() == 0){
			/** Occurred some validation error. */			
			crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.withoutstudent.validation");
			throw crudException;
		}
		
		/** Rule 3: The maximum number of participants can not be exceeded. Verification realized only for students. */	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		Professor professorLogged = (Professor) session.getAttribute("professor");
		
		if(professorLogged == null){
			/** Verifies that occurred some validation error. */
			if(studentsGroup.size() > Integer.parseInt(entity.getAssignment().getNumMaxParticipants())){
				crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.numMaxParticipants.validation", Integer.parseInt(entity.getAssignment().getNumMaxParticipants()));
				throw crudException;
			}
		}
		
		/** Rule 4: Checks for registered student at some other group. */
		try{
			/** Returns all assignmentGroups of the assignment. */
			List<AssignmentGroup> listAssignmentGroups = assignmentGroupDAO.retrieveByAssignment(entity.getAssignment());
			
			for(Student student : studentsGroup){
				for(AssignmentGroup group : listAssignmentGroups){
					List<Student> auxStudents = new ArrayList<Student>(group.getStudents());
					
					/** Verifies that occurred some validation error. */
					if(auxStudents.contains(student)){
						crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.student.validation", student, group.getNumber());
						throw crudException;
					}
				}
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Validates business rules in updating the entity. */
	@Override
	public void validateUpdate(AssignmentGroup entity) throws CrudException {
		/** Possibly throwing a CRUD Exception to indicate validation error. */
		CrudException crudException = null;
		AssignmentGroup anotherEntity = null;
			
		/** Rule 1: Cannot have two assignment groups with the same assignment and number. */
		try {
			anotherEntity = assignmentGroupDAO.retrieveByNumber(entity.getAssignment(), entity.getNumber());
		} 
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
		
		if (anotherEntity != null) {
			float entityID = entity.getId();
			float anotherEntityID = anotherEntity.getId();
			
			/** Verifies that occurred some validation error. */
			if (entityID != anotherEntityID) {
				crudException = addValidationError(crudException, "", "number", "manageAssignmentGroups.form.number.validation");
				throw crudException;
			}
		}
		
		/** Returns the registered students in the group */
		List<Student> studentsGroup = new ArrayList<Student>(entity.getStudents());
		
		/** Rule 2: Checks whether the assignmentGroup is without students. */
		if(studentsGroup.size() == 0){
			/** Occurred some validation error. */			
			crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.withoutstudent.validation");
			throw crudException;
		}
		
		/** Rule 2: Checks whether the assignmentGroup is without students. */
		if(studentsGroup.size() == 0){
			/** Occurred some validation error. */			
			crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.withoutstudent.validation");
			throw crudException;
		}
		
		/** Rule 3: The maximum number of participants can not be exceeded. Verification realized only for students. */	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		Professor professorLogged = (Professor) session.getAttribute("professor");
		
		if(professorLogged == null){			
			/** Verifies that occurred some validation error. */
			if(studentsGroup.size() > Integer.parseInt(entity.getAssignment().getNumMaxParticipants())){
				crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.numMaxParticipants.validation", Integer.parseInt(entity.getAssignment().getNumMaxParticipants()));
				throw crudException;
			}
		}
		
		/** Rule 4: Checks for registered student at some other group. */
		try{
			/** Returns all assignmentGroups of the assignment. */
			List<AssignmentGroup> listAssignmentGroups = assignmentGroupDAO.retrieveByAssignment(entity.getAssignment());
			
			for(Student student : studentsGroup){
				for(AssignmentGroup group : listAssignmentGroups){
					List<Student> auxStudents = new ArrayList<Student>(group.getStudents());
					
					/** Verifies that occurred some validation error. */
					if(auxStudents.contains(student) && (! entity.getNumber().equals(group.getNumber()))){
						crudException = addValidationError(crudException, "", "students", "manageAssignmentGroups.form.student.validation", student, group.getNumber());
						throw crudException;
					}
				}
			}
		}
		catch (PersistentObjectNotFoundException e) {}
		catch (MultiplePersistentObjectsFoundException e) {}
	}
	
	/** Returns list of all assignmentGroups. */
	public List<AssignmentGroup> getAssignmentGroups(){
		return assignmentGroupDAO.retrieveAll();
	}
}