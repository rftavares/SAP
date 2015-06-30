package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageCoursesService;
import br.ufes.inf.nemo.sap.assignments.domain.Course;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Courses".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageCoursesController extends CrudController<Course> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Courses" service. */
	@EJB
	private ManageCoursesService manageCoursesService;
	
	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();
	
	/** Getter class service. */
	@Override
	protected CrudService<Course> getCrudService() {
		return manageCoursesService;
	}
	
	/** Class constructor. */
	public ManageCoursesController() {
	    viewPath = "/assignments/manageCourses/";
	    bundleName = "msgs";
	}

	/** Creates a new entity Course. */
	@Override
	protected Course createNewEntity() {
		return new Course();
	}
	
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {		
		String errorMessageKey = manageCoursesService.validateExclusion(selectedEntity);		
		
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
		addFilter(new LikeFilter(	"manageCourses.filter.byCode", "code", 
									getI18nMessage("msgs", "manageCourses.form.code")));
		addFilter(new LikeFilter(	"manageCourses.filter.byName", "name", 
									getI18nMessage("msgs", "manageCourses.form.name")));	
	}
	
	/** List of courses. */
	private List<Course> courses;
	
	/** Getter for list of courses. */
	public List<Course> getCourses() {
		courses = manageCoursesService.getCourses();
		return courses; 
	}	
}