package br.ufes.inf.nemo.sap.lab.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import br.ufes.inf.nemo.sap.assignments.application.ManageProfessorsService;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.lab.application.ManageResearchGroupsService;
import br.ufes.inf.nemo.sap.lab.domain.*;
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
	
	@EJB
	private ManageProfessorsService manageProfessorsService;

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
	
	/** List of professors. */
	private DualListModel<Professor> professors;
	
	/** Getter for list of professors. */
	public DualListModel<Professor> getProfessors() {
		return professors;
	}

	/** Setter for list of professors. */
	public void setProfessors(DualListModel<Professor> professors) {
		this.professors = professors;
	}

	/** Creates a new entity Research Groups. */
	@Override
	protected ResearchGroup createNewEntity() {
		professors = new DualListModel<Professor>(manageProfessorsService.getProfessors(), new ArrayList<Professor>());
		
		return new ResearchGroup();
	}
	
	/** Updates the entity with the value of the selected field on the page. */
	@Override
    public String save() {
		System.out.println("SAVEEEEE - LISTA DE PROFESSORES:");
		System.out.println(professors.getTarget());
		selectedEntity.setProfessors(new HashSet<Professor>(professors.getTarget()));
		return super.save();
	}
	
	/** Method called when modifying an entity. Loads the page's fields with entity data, if it has been selected. */
    @Override
    protected void checkSelectedEntity() {
    	/** Retrieves the professors in the selected researchGroup. */
    	List<Professor> professorsGroup = new ArrayList<Professor>(selectedEntity.getProfessors());
    	professors = new DualListModel<Professor>(new ArrayList<Professor>(), new ArrayList<Professor>());
    	
    	/** Carries all professors. */
    	List<Professor> sourceList = manageProfessorsService.getProfessors();
    	
    	/** Remove from the list the professors who are already in the researchGroup. */
    	for(Professor item : professorsGroup){
    		sourceList.remove(item);
    	}
    	
    	/** Loads the data on the page component. */
    	professors.setSource(sourceList);
    	professors.setTarget(professorsGroup);
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