package br.ufes.inf.nemo.sap.assignments.domain;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Course domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(Course.class)
public class Course_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<Course, String> name;
	public static volatile SingularAttribute<Course, String> code;
	public static volatile SetAttribute<Course, SchoolRoom> schoolRooms;
}