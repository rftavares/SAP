package br.ufes.inf.nemo.sap.lab.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.lab.domain.Supervision;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Supervisions" use case.
 * 
 * This use case consists of a CRUD for the class Supervision and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManageSupervisionsService extends CrudService<Supervision> {
	/************************************************************************************************** 
	 * Method used to return a list of all supervisions.
	 * 
	 * @return 
	 * 		A list of all supervisions objects.
	 ***************************************************************************************************/
	public List<Supervision> getSupervisions();
}