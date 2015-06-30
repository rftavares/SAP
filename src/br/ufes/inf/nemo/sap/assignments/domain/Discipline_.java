package br.ufes.inf.nemo.sap.assignments.domain;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Discipline domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(Discipline.class)
public class Discipline_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<Discipline, String> name;
	public static volatile SingularAttribute<Discipline, String> code;
	public static volatile SetAttribute<Discipline, SchoolRoom> schoolRooms;
}