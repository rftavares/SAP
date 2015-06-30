package br.ufes.inf.nemo.sap.assignments.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.sap.assignments.domain.DeliveredAssignment;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

/**
 * Interface for a DAO for objects of the DeliveredAssignment domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, 
 * whereas operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Local
public interface DeliveredAssignmentDAO extends BaseDAO<DeliveredAssignment> {

}