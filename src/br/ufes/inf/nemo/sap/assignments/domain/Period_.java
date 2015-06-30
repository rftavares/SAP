package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.*;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Period domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(Period.class)
public class Period_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<Period, String> year;
	public static volatile SingularAttribute<Period, String> number;
	public static volatile SingularAttribute<Period, Date> startDate;
	public static volatile SingularAttribute<Period, Date> endDate;
	public static volatile SetAttribute<Period, SchoolRoom> schoolRooms;
}