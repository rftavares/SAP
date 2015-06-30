package br.ufes.inf.nemo.sap.lab.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageProfessorsService;
import br.ufes.inf.nemo.sap.assignments.domain.Professor;
import br.ufes.inf.nemo.sap.lab.application.ManageResearchGroupsService;
import br.ufes.inf.nemo.sap.lab.domain.ResearchGroup;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
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
public class AssociateProfessorsController extends CrudController<Professor> {	
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Professors" service. */
	@EJB
	private ManageProfessorsService manageProfessorsService;
	
	/** The "Manage Research Group" service. */
	@EJB
	private ManageResearchGroupsService manageResearchGroupsService;
	
	/** The "Associate Professors" service. */
	//@EJB
	//private AssociateProfessorsController associateProfessorsController;
	
	/** Getter class service. */
	@Override
	protected CrudService<Professor> getCrudService() {
		return manageProfessorsService;
	}
	
	/** Class constructor. */
	public AssociateProfessorsController() {
	    viewPath = "/labs/associateProfessors/";
	    bundleName = "msgs";
	}
	
	/** Professor who will be associated. */
	private Professor professor;

	/** Getter for Professor */
	public Professor getProfessor() {
		return professor;
	}

	/** Setter for Professor */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	/** ResearchGroup who will be associated. */
	private ResearchGroup researchGroup;

	/** Getter for ResearchGroup */
	public ResearchGroup getResearchGroup() {
		return researchGroup;
	}

	/** Setter for ResearchGroup */
	public void setResearchGroup(ResearchGroup researchGroup) {
		this.researchGroup = researchGroup;
	}

	/** Creates a new entity Professor. */
	@Override
	protected Professor createNewEntity() {
		return new Professor();
	}
		
	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		
	}
	
	/** List of professors. */
	private List<Professor> professors;
	
	/** Getter for list of professors. */
	public List<Professor> getProfessors() {
		professors = manageProfessorsService.getProfessors();
		return professors; 
	}
	
	/** List of researchGroups. */
	private List<ResearchGroup> researchGroups;
	
	/** Getter for list of researchGroups. */
	public List<ResearchGroup> getResearchGroups() {
		researchGroups = manageResearchGroupsService.getResearchGroups();
		return researchGroups; 
	}
}