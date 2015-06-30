package br.ufes.inf.nemo.sap.lab.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.lab.application.ManageSupervisionsService;
import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Supervisions".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageSupervisionsController extends CrudController<Supervision> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Supervisions" service. */
	@EJB
	private ManageSupervisionsService manageSupervisionsService;
		
	public SupervisionTypeEnum[] getSupervisionTypes(){		
        return SupervisionTypeEnum.values();        
    }
	
	/** Getter class service. */
	@Override
	protected CrudService<Supervision> getCrudService() {
		return manageSupervisionsService;
	}
	
	/** Class constructor. */
	public ManageSupervisionsController() {
	    viewPath = "/labs/manageSupervisions/";
	    bundleName = "msgs";
	}
	
	/** Creates a new entity Supervision. */
	@Override
	protected Supervision createNewEntity() {
		return new Supervision();
	}
	
	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter(	"manageSupervisions.filter.byStudent", "student.name",
									getI18nMessage("msgs", "manageSupervisions.form.name")));
		addFilter(new LikeFilter(	"manageSupervisions.filter.byTheme", "theme",
									getI18nMessage("msgs", "manageSupervisions.form.theme")));
		addFilter(new LikeFilter(	"manageSupervisions.filter.byAdvisor", "advisor.name",
									getI18nMessage("msgs", "manageSupervisions.form.advisor")));
		addFilter(new LikeFilter(	"manageSupervisions.filter.byCoadvisor", "coadvisor.name", 
									getI18nMessage("msgs", "manageSupervisions.form.coadvisor")));
	}
}