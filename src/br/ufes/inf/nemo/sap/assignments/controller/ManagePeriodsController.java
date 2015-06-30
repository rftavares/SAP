package br.ufes.inf.nemo.sap.assignments.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManagePeriodsService;
import br.ufes.inf.nemo.sap.assignments.domain.Period;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Periods".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManagePeriodsController extends CrudController<Period> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Periods" service. */
	@EJB
	private ManagePeriodsService managePeriodsService;
	
	/** Controller with SAP utilities. */
	SAPUtilsController sapUtilsController = new SAPUtilsController();

	/** Getter class service. */
	@Override
	protected CrudService<Period> getCrudService() {
		return managePeriodsService;
	}
	
	/** Class constructor. */
	public ManagePeriodsController() {
	    viewPath = "/assignments/managePeriods/";
	    bundleName = "msgs";
	}
	
	/** Creates a new entity Period. */
	@Override
	protected Period createNewEntity() {
		return new Period();
	}
	
	/** Validates rules to delete the entity. */
	@Override
	public String delete() {		
		String errorMessageKey = managePeriodsService.validateExclusion(selectedEntity);		
		
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
		addFilter(new LikeFilter(	"managePeriods.filter.byYear", "year", 
									getI18nMessage("msgs", "managePeriods.form.year")));
		addFilter(new LikeFilter(	"managePeriods.filter.byNumber", "number", 
									getI18nMessage("msgs", "managePeriods.form.number")));
	}
	
	/** List of periods. */
	private List<Period> periods;
	
	/** Getter for list of periods. */
	public List<Period> getPeriods() {
		periods = managePeriodsService.getPeriods();
		return periods; 
	}	
}