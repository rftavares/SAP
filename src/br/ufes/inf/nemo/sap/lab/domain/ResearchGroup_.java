package br.ufes.inf.nemo.sap.lab.domain;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the ResearchGroup domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(ResearchGroup.class)
public class ResearchGroup_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<ResearchGroup, String> name;
	public static volatile SingularAttribute<ResearchGroup, String> site;
	public static volatile SetAttribute<ResearchGroup, Professor> professors;
}