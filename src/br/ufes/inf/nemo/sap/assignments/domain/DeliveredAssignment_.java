package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Date;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the DeliveredAssignment domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(DeliveredAssignment.class)
public class DeliveredAssignment_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<DeliveredAssignment, String> file;
	public static volatile SingularAttribute<DeliveredAssignment, Date> deliveryDate;
	public static volatile SingularAttribute<DeliveredAssignment, String> md5;
	public static volatile SingularAttribute<DeliveredAssignment, AssignmentGroup> assignmentGroup;
}