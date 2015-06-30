package br.ufes.inf.nemo.sap.assignments.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.application.ManageDeliveredAssignmentsService;
import br.ufes.inf.nemo.sap.assignments.domain.DeliveredAssignment;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Delivered Assignments".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@SessionScoped
public class ManageDeliveredAssignmentsController extends CrudController<DeliveredAssignment> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage Delivered Assignments" service. */
	@EJB
	private ManageDeliveredAssignmentsService manageDeliveredAssignmentsService;

	/** Getter class service. */
	@Override
	protected CrudService<DeliveredAssignment> getCrudService() {
		return manageDeliveredAssignmentsService;
	}
	
	/** Class constructor. */
	public ManageDeliveredAssignmentsController() {
	    viewPath = "/assignments/manageDeliveredAssignments/";
	    bundleName = "msgs";
	}
	
	// VERIFICAR DEPOIS COMO FAZ O UPLOAD DE ARQUIVOS
	//private UploadedFile file;
	 
    //public UploadedFile getFile() {
    //    return file;
    //}
 
    //public void setFile(UploadedFile file) {
    //    this.file = file;
    //}
	
	/** Creates a new entity DeliveredAssignment. */
	@Override
	protected DeliveredAssignment createNewEntity() {
		return new DeliveredAssignment();
	}

	/** Filters used in the class. */
	@Override
	protected void initFilters() {
		
	}
}