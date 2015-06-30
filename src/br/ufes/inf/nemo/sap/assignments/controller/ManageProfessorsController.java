package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageProfessorsService;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Professors".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageProfessorsController extends CrudController<Professor> {	
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Professors" service. */
	@EJB
	private ManageProfessorsService manageProfessorsService;
	
	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();
	
	public AdministratorEnum[] getAdministrators(){		
        return AdministratorEnum.values();        
    }
	
	/** Getter class service. */
	@Override
	protected CrudService<Professor> getCrudService() {
		return manageProfessorsService;
	}
	
	/** Class constructor. */
	public ManageProfessorsController() {
	    viewPath = "/assignments/manageProfessors/";
	    bundleName = "msgs";
	}

	/** Creates a new entity Professor. */
	@Override
	protected Professor createNewEntity() {
		return new Professor();
	}
			
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {		
		String errorMessageKey = manageProfessorsService.validateExclusion(selectedEntity);		
		
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
		addFilter(new LikeFilter(	"manageProfessors.filter.byName", "name", 
									getI18nMessage("msgs", "manageProfessors.form.name")));
		addFilter(new LikeFilter(	"manageProfessors.filter.byEmail", "email", 
									getI18nMessage("msgs", "manageProfessors.form.email")));	
	}
	
	/** List of professors. */
	private List<Professor> professors;
	
	/** Getter for list of professors. */
	public List<Professor> getProfessors() {
		professors = manageProfessorsService.getProfessors();		
		return professors; 
	}
}