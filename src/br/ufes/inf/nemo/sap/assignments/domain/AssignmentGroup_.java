package br.ufes.inf.nemo.sap.assignments.domain;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the AssignmentGroup domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(AssignmentGroup.class)
public class AssignmentGroup_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<AssignmentGroup, String> number;
	public static volatile SingularAttribute<AssignmentGroup, Assignment > assignment;	
	public static volatile SetAttribute<AssignmentGroup, Student> students;
	//public static volatile SetAttribute<AssignmentGroup, DeliveredAssignment> deliveredAssignment;	
}