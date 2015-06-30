package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageDisciplinesService;
import br.ufes.inf.nemo.sap.assignments.domain.Discipline;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Disciplines".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageDisciplinesController extends CrudController<Discipline> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Disciplines" service. */
	@EJB
	private ManageDisciplinesService manageDisciplinesService;
	
	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();
	
	/** Getter class service. */
	@Override
	protected CrudService<Discipline> getCrudService() {		
		return manageDisciplinesService;
	}
	
	/** Class constructor. */
	public ManageDisciplinesController() {
	    viewPath = "/assignments/manageDisciplines/";
	    bundleName = "msgs";
	}
	
	/** Creates a new entity Discipline. */
	@Override
	protected Discipline createNewEntity() {
		return new Discipline();
	}
	
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {		
		String errorMessageKey = manageDisciplinesService.validateExclusion(selectedEntity);		
		
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
		addFilter(new LikeFilter(	"manageDisciplines.filter.byCode", "code", 
									getI18nMessage("msgs", "manageDisciplines.form.code")));
		addFilter(new LikeFilter(	"manageDisciplines.filter.byName", "name", 
									getI18nMessage("msgs", "manageDisciplines.form.name")));
	}
	
	/** List of disciplines. */
	private List<Discipline> disciplines;
	
	/** Getter for list of disciplines. */
	public List<Discipline> getDisciplines() {
		disciplines = manageDisciplinesService.getDisciplines();
		return disciplines;
	}	
}