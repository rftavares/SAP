package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

import br.ufes.inf.nemo.sap.assignments.application.*;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Assignment Groups".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageAssignmentGroupsController extends CrudController<AssignmentGroup> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Assignment Groups" service. */
	@EJB
	private ManageAssignmentGroupsService manageAssignmentGroupsService;
	
	/** The "Manage SchoolRooms" service. */
	@EJB
	private ManageSchoolRoomsService manageSchoolRoomsService;
	
	/** The "Manage Students" service. */
	@EJB
	private ManageStudentsService manageStudentsService;
	
	/** The "Manage Assignments" service. */
	@EJB
	private ManageAssignmentsService manageAssignmentsService;
	
	/** Getter class service. */
	@Override
	protected CrudService<AssignmentGroup> getCrudService() {
		return manageAssignmentGroupsService;
	}
		
	/** Class constructor. */
	public ManageAssignmentGroupsController() {
	    viewPath = "/assignments/manageAssignmentGroups/";
	    bundleName = "msgs";
	}
	
	/** Creates a new entity AssignmentGroup and clean the page fields. */
	@Override
	protected AssignmentGroup createNewEntity() {
		periodField = null;
		schoolRoomField = null;
		assignmentField = null;
		
		if(schoolRooms != null) {
			schoolRooms.clear();
		}
		if(assignments != null){
			assignments.clear();
		}
		return new AssignmentGroup();
	}
	
	/** Period variable used to store the current value of the entity on the page. */
	private Period periodField;
	
	/** Getter for periodField. */
	public Period getPeriodField() {
		return periodField;
	}

	 /** Setter for periodField. */
	public void setPeriodField(Period periodField) {
		this.periodField = periodField;
	}
	
	/** Schoolroom variable used to store the current value of the entity on the page. */
	private SchoolRoom schoolRoomField;
    
	/** Getter for schoolRoomField. */
    public SchoolRoom getSchoolRoomField() {
		return schoolRoomField;
	}

    /** Setter for schoolRoomField. */
	public void setSchoolRoomField(SchoolRoom schoolRoomField) {
		this.schoolRoomField = schoolRoomField;
	}
	
	/** List used to display the schoolRooms on the page. */
	private List<SchoolRoom> schoolRooms;

	/** Getter for schoolRooms. */
	public List<SchoolRoom> getSchoolRooms() {
		return schoolRooms;
	}

	/** Setter for schoolRooms. */
	public void setSchoolRooms(List<SchoolRoom> schoolRooms) {
		this.schoolRooms = schoolRooms;
	}
	
	/** Assignment variable used to store the current value of the entity on the page. */
	private Assignment assignmentField;
    
	/** Getter for assignmentField. */
    public Assignment getAssignmentField() {
		return assignmentField;
	}

    /** Setter for assignmentField. */
	public void setAssignmentField(Assignment assignmentField) {
		this.assignmentField = assignmentField;
	}
	
	/** List used to display the assignments on the page. */
	private List<Assignment> assignments;
			
	
	/** Getter for assignments. */
	public List<Assignment> getAssignments() {
		return assignments;
	}

	/** Setter for assignments. */
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}	
	
	/** List of students. */
	private DualListModel<Student> students;
	
	/** Getter for list of students. */
	public DualListModel<Student> getStudents() {
		List<Student> studentsTarget = manageStudentsService.getStudents(); 
		
		students = new DualListModel<Student>(studentsTarget, new ArrayList<Student>());
		return students;
	}
	
	/** Setter for list of students. */
	public void setStudents(DualListModel<Student> students) {
		this.students = students;
	}
	
	/** Updates the entity with the value of the selected field on the page. */
	@Override
    public String save() {
    	selectedEntity.setAssignment(assignmentField);    	
    	return super.save();
    }
    
    /** Method called when modifying an entity. Loads the page's fields with entity data, if it has been selected. */
    @Override
    protected void checkSelectedEntity() {
    	periodField = selectedEntity.getAssignment().getSchoolRoom().getPeriod();
    	schoolRoomField = selectedEntity.getAssignment().getSchoolRoom();    	
    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(selectedEntity.getAssignment().getSchoolRoom().getPeriod());
    	assignmentField = selectedEntity.getAssignment();
    	assignments = manageAssignmentsService.getAssignmentsSchoolRoom(selectedEntity.getAssignment().getSchoolRoom());
    }
    
    /** Method used to return a list of the schoolRoom of a selected period. */
	public void loadSchoolRooms(AjaxBehaviorEvent event) {		
		/** Returns the component that triggered the event ajax. */
		Map<String, Object> map = event.getComponent().getAttributes();
		Period periodEntity = (Period) map.get("value");
		
		/** Clean the schoolRoom list data, if exists. */
		if(schoolRooms != null) {
			schoolRooms.clear();
			schoolRoomField = null;
		}
		
		/** Clean the assignment list data, if exists. */
		if(assignments != null) {
			assignments.clear();
			assignmentField = null;
		}
		
		/** Get the session. */
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	    
	    /** Get the professor logged. */
	    Professor professor = (Professor) session.getAttribute("professor");
	    
	    /** Checks whether the professor is administrator. */
	    if(professor.getAdministrator() == AdministratorEnum.YES){
	    	/** Loads all classes of the selected period in the list. */
	    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(periodEntity);
	    }
	    else{
	    	/** Loads in the list all classes of the selected period and professor. */
	    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(periodEntity, professor);
	    }
	}
    
	/** Method used to return a list of the assignments of a selected schoolRoom. */
	public void loadAssignments(AjaxBehaviorEvent event) {
		
		/** Returns the component that triggered the event ajax. */
		Map<String, Object> map = event.getComponent().getAttributes();
		SchoolRoom schoolRoomEntity = (SchoolRoom) map.get("value");
		
		/** Clean the list data, if exists. */
		if(assignments != null) {
			assignments.clear();
			assignmentField = null;
		}
		
    	/** Loads all assignments of the selected schoolRoom in the list. */
		assignments = manageAssignmentsService.getAssignmentsSchoolRoom(schoolRoomEntity);	    
	}
	
	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter(	"manageAssignmentGroups.filter.byNumber", 
									"number", getI18nMessage("msgs", "manageAssignmentGroups.form.number")));		
	}
}