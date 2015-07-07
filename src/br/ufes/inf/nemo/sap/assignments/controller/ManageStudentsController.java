package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageStudentsService;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Students".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageStudentsController extends CrudController<Student> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Students" service. */
	@EJB
	private ManageStudentsService manageStudentsService;
	
	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();
	
	/** Getter class service. */
	@Override
	protected CrudService<Student> getCrudService() {
		return manageStudentsService;
	}
	
	/** Class constructor. */
	public ManageStudentsController() {
	    viewPath = "/assignments/manageStudents/";
	    bundleName = "msgs";
	}

	/** Creates a new entity Student. */
	@Override
	protected Student createNewEntity() {
		return new Student();
	}

	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter(	"manageStudents.filter.byEnrollment", "enrollment",
									getI18nMessage("msgs", "manageStudents.form.enrollment")));
		addFilter(new LikeFilter(	"manageStudents.filter.byName", "name", 
									getI18nMessage("msgs", "manageStudents.form.name")));
		addFilter(new LikeFilter(	"manageStudents.filter.byEmail", "email", 
									getI18nMessage("msgs", "manageStudents.form.email")));		
		addFilter(new LikeFilter(	"manageStudents.filter.byPhone", "phone", 
									getI18nMessage("msgs", "manageStudents.form.phone")));
	}
	
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {		
		String errorMessageKey = manageStudentsService.validateExclusion(selectedEntity);		
		
		if(errorMessageKey.equals("")) {
			return super.delete();
		}
		else {
			sapUtilsController.showGlobalMessage(FacesMessage.SEVERITY_FATAL, errorMessageKey);
			return "";
		}		
	}
	
	/** List of students. */
	private List<Student> students;
	
	/** Getter for list of professors. */
	public List<Student> getStudents() {
		students = manageStudentsService.getStudents();		
		return students; 
	}
}