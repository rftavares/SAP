package br.ufes.inf.nemo.sap.assignments.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import br.ufes.inf.nemo.sap.assignments.application.ManageSchoolRoomsService;
import br.ufes.inf.nemo.sap.assignments.application.ManageStudentsService;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Schoolrooms".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageSchoolRoomsController extends CrudController<SchoolRoom> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage SchoolRooms" service. */
	@EJB
	private ManageSchoolRoomsService manageSchoolRoomsService;
	
	/** The "Manage Students" service. */
	@EJB
	private ManageStudentsService manageStudentsService;

	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();
	
	/** Getter class service. */
	@Override
	protected CrudService<SchoolRoom> getCrudService() {
		return manageSchoolRoomsService;
	}
	
	/** Class constructor. */
	public ManageSchoolRoomsController() {
	    viewPath = "/assignments/manageSchoolRooms/";
	    bundleName = "msgs";
	}
	
	/** List of students. */
	private DualListModel<Student> students;
		
	/** Getter for list of students. */
	public DualListModel<Student> getStudents() {		
		return students;
	}
	
	/** Setter for list of students. */
	public void setStudents(DualListModel<Student> students) {
		this.students = students;
	}

	/** Creates a new entity SchoolRoom. */
	@Override
	protected SchoolRoom createNewEntity() {
		students = new DualListModel<Student>(manageStudentsService.getStudents(), new ArrayList<Student>());
		
		return new SchoolRoom();
	}
	
	/** Updates the entity with the value of the selected field on the page. */
	@Override
    public String save() {
		/** List of students in the schoolRoom without the changes made on the page. */
    	List<Student> studentsSchoolRoom = new ArrayList<Student>(selectedEntity.getStudents());
    	
    	String errorMessageKey = "";
    	
    	/** Checks if any student was removed from schoolRoom and is in any assignmentGroup. */
    	for(Student student : studentsSchoolRoom){
    		if(! students.getTarget().contains(student)) {
    			errorMessageKey = manageSchoolRoomsService.validateExclusionStudent(student, selectedEntity);
    			
    			if(! errorMessageKey.equals("")){
    				sapUtilsController.showGlobalMessage(FacesMessage.SEVERITY_FATAL, errorMessageKey);
    				return "";
    			}
    		}
    	}
    	
		/** Updates the students in the class schoolRoom. */
    	selectedEntity.setStudents(new HashSet<Student>(students.getTarget()));
    	
    	/** Stores the entity in the database. */
    	return super.save();
    }
	
	/** Method called when modifying an entity. Loads the page's fields with entity data, if it has been selected. */
    @Override
    protected void checkSelectedEntity() {
    	/** Retrieves the students in the selected schoolRoom. */
    	List<Student> studentsSchoolRoom = new ArrayList<Student>(selectedEntity.getStudents());
    	students = new DualListModel<Student>(new ArrayList<Student>(), new ArrayList<Student>());
    	
    	/** Carries all students in the selected schoolRoom. */
    	List<Student> sourceList = manageStudentsService.getStudents();
    	
    	/** Remove from the list the students who are already in the schoolRoom. */
    	for(Student item : studentsSchoolRoom){
    		sourceList.remove(item);
    	}
    	
    	/** Loads the data on the page component. */
    	students.setSource(sourceList);
    	students.setTarget(studentsSchoolRoom);
    }
		
	/** Filters used in the class. */
	@Override
	protected void initFilters() {		
		addFilter(new LikeFilter(	"manageSchoolRooms.filter.byProfessor", "professor.name", 
									getI18nMessage("msgs", "manageSchoolRooms.form.professor")));
		addFilter(new LikeFilter(	"manageSchoolRooms.filter.byPeriod", "period.year", 
									getI18nMessage("msgs", "manageSchoolRooms.filter.period")));		
		addFilter(new LikeFilter(	"manageSchoolRooms.filter.byCourse", "course.name", 
									getI18nMessage("msgs", "manageSchoolRooms.filter.course.name")));		
		addFilter(new LikeFilter(	"manageSchoolRooms.filter.byDiscipline", "discipline.name", 
									getI18nMessage("msgs", "manageSchoolRooms.filter.discipline.name")));
		addFilter(new LikeFilter(	"manageSchoolRooms.filter.byNumber", "number", 
									getI18nMessage("msgs", "manageSchoolRooms.form.number")));
	}
	
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {		
		String errorMessageKey = manageSchoolRoomsService.validateExclusion(selectedEntity);		
		
		if(errorMessageKey.equals("")) {
			return super.delete();
		}
		else {
			sapUtilsController.showGlobalMessage(FacesMessage.SEVERITY_FATAL, errorMessageKey);
			return "";
		}		
	}
	
	/** List of schoolRooms. */
	private List<SchoolRoom> schoolRooms;
	
	/** Getter for list of professors. */
	public List<SchoolRoom> getSchoolRooms() {
		schoolRooms = manageSchoolRoomsService.getSchoolRooms();		
		return schoolRooms; 
	}
}