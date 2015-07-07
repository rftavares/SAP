package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufes.inf.nemo.sap.assignments.application.*;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Assignments".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageAssignmentsController extends CrudController<Assignment> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Assignments" service. */
	@EJB 
	private ManageAssignmentsService manageAssignmentsService;
	
	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();
	
	/** The "Manage SchoolRooms" service. */
	@EJB
	private ManageSchoolRoomsService manageSchoolRoomsService;
		
	/** Getter class service. */
	@Override
	protected CrudService<Assignment> getCrudService() {		
		return manageAssignmentsService;
	}
	
	/** Class constructor. */
	public ManageAssignmentsController() {
	    viewPath = "/assignments/manageAssignments/";
	    bundleName = "msgs";
	}
	
	/** Creates a new entity Assignment and clean the page fields. */
	@Override
	protected Assignment createNewEntity() {
		periodField = null;
		schoolRoomField = null;
		
		if(schoolRooms != null) {
			schoolRooms.clear();
		}
		
		return new Assignment();
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
	
	/** Updates the entity with the value of the selected field on the page. */
	@Override
    public String save() {
    	selectedEntity.setSchoolRoom(schoolRoomField);    	
    	return super.save();
    }
    
    /** Method called when modifying an entity. Loads the page's fields with entity data, if it has been selected. */
    @Override
    protected void checkSelectedEntity() {    	
    	periodField = selectedEntity.getSchoolRoom().getPeriod();
    	schoolRoomField = selectedEntity.getSchoolRoom();    	
    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(selectedEntity.getSchoolRoom().getPeriod());
    }	
	
	/** Method used to return a list of the schoolRoom of a selected period. */
	public void loadSchoolRooms(AjaxBehaviorEvent event) {
		/** Returns the component that triggered the event ajax. */
		Map<String, Object> map = event.getComponent().getAttributes();
		Period periodEntity = (Period) map.get("value");
		
		/** Clean the list data, if exists. */
		if(schoolRooms != null) {
			schoolRooms.clear();
			schoolRoomField = null;
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
	    	/** Loads in the list all schoolRooms of the selected period and professor. */
	    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(periodEntity, professor);
	    }
	}
	
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {
		String errorMessageKey = manageAssignmentsService.validateExclusion(selectedEntity);		
		
		if(errorMessageKey.equals("")) {
			return super.delete();
		}
		else {
			sapUtilsController.showGlobalMessage(FacesMessage.SEVERITY_FATAL, errorMessageKey);
			return "";
		}
	}

	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter(	"manageAssignments.filter.byPeriod", "schoolRoom.period.year",
				 					getI18nMessage("msgs", "manageAssignments.filter.period.year")));
		addFilter(new LikeFilter(	"manageAssignments.filter.byProfessor", "schoolRoom.professor.name",
									getI18nMessage("msgs", "manageAssignments.filter.professor.name")));
		addFilter(new LikeFilter(	"manageAssignments.filter.byCourse", "schoolRoom.course.name",
									getI18nMessage("msgs", "manageAssignments.filter.course.name")));
		addFilter(new LikeFilter(	"manageAssignments.filter.bySchoolRoom", "schoolRoom.discipline.name",
				 					getI18nMessage("msgs", "manageAssignments.filter.schoolRoom.name")));
		addFilter(new LikeFilter(	"manageAssignments.filter.byNumber", "number",
				 					getI18nMessage("msgs", "manageAssignments.form.number")));
		addFilter(new LikeFilter(	"manageAssignments.filter.bySubject", "subject",
									getI18nMessage("msgs", "manageAssignments.form.subject")));
	}
}