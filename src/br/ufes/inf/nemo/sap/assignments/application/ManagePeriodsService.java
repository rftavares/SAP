package br.ufes.inf.nemo.sap.assignments.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Periods" use case.
 * 
 * This use case consists of a CRUD for the class Period and uses the mini CRUD framework for EJB3.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface ManagePeriodsService extends CrudService<Period> {
	/************************************************************************************************** 
	 * Method used to validates rules to delete the entity.
	 * 
	 * @param period
	 *      Period that will be deleted.
	 * 
	 * @return 
	 * 		String with the validation error message key, if exists.
	 ***************************************************************************************************/
	public String validateExclusion(Period period);
	
	/************************************************************************************************** 
	 * Method used to return a list of all periods.
	 * 
	 * @return 
	 * 		A list of all periods objects.
	 ***************************************************************************************************/	
	public List<Period> getPeriods();
}