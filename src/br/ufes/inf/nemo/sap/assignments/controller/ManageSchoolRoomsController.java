package br.ufes.inf.nemo.sap.assignments.controller;


import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageSchoolRoomsService;
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

	/** Creates a new entity SchoolRoom. */
	@Override
	protected SchoolRoom createNewEntity() {
		return new SchoolRoom();
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