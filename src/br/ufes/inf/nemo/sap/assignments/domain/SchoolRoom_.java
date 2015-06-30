package br.ufes.inf.nemo.sap.assignments.domain;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the SchoolRoom domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(SchoolRoom.class)
public class SchoolRoom_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<SchoolRoom, String> number;
	public static volatile SingularAttribute<SchoolRoom, Period> period;
	public static volatile SingularAttribute<SchoolRoom, Discipline> discipline;
	public static volatile SingularAttribute<SchoolRoom, Professor> professor;
	public static volatile SingularAttribute<SchoolRoom, Course> course;
	public static volatile SetAttribute<SchoolRoom, Assignment> assignments;
}