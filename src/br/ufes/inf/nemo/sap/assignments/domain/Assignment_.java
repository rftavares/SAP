package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.*;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Assignment domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(Assignment.class)
public class Assignment_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Assignment, String> number;
	public static volatile SingularAttribute<Assignment, String> subject;
	public static volatile SingularAttribute<Assignment, Float > value;
	public static volatile SingularAttribute<Assignment, Date > deliveryDate;
	public static volatile SingularAttribute<Assignment, String > description;
	public static volatile SingularAttribute<Assignment, String > numMaxParticipants;
	public static volatile SingularAttribute<Assignment, Float > valueDiscountDelay;
	public static volatile SingularAttribute<Assignment, SchoolRoom> schoolRoom;
	public static volatile SetAttribute<Assignment, AssignmentGroup> assignmentGroups;
}