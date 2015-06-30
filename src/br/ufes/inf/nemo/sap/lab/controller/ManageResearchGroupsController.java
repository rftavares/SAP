package br.ufes.inf.nemo.sap.lab.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.lab.application.ManageResearchGroupsService;
import br.ufes.inf.nemo.sap.lab.domain.ResearchGroup;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Research Groups".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageResearchGroupsController extends CrudController<ResearchGroup> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Research Groups" service. */
	@EJB
	private ManageResearchGroupsService manageResearchGroupsService;

	/** Getter class service. */
	@Override
	protected CrudService<ResearchGroup> getCrudService() {
		return manageResearchGroupsService;
	}
	
	/** Class constructor. */
	public ManageResearchGroupsController() {
	    viewPath = "/labs/manageResearchGroups/";
	    bundleName = "msgs";
	}
	
	/** Creates a new entity Research Groups. */
	@Override
	protected ResearchGroup createNewEntity() {
		return new ResearchGroup();
	}

	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter(	"manageResearchGroups.filter.byName", "name", 
									getI18nMessage("msgs", "manageResearchGroups.form.name")));
		addFilter(new LikeFilter(	"manageResearchGroups.filter.bySite", "site", 
									getI18nMessage("msgs", "manageResearchGroups.form.site")));		
	}
}