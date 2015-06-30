package br.ufes.inf.nemo.sap.lab.domain;

import java.util.Date;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Supervision domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(Supervision.class)
public class Supervision_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<Supervision, String> theme;
	public static volatile SingularAttribute<Supervision, SupervisionTypeEnum> type;
	public static volatile SingularAttribute<Supervision, Student> student;
	public static volatile SingularAttribute<Supervision, Professor> advisor;
	public static volatile SingularAttribute<Supervision, Professor> coadvisor;
	public static volatile SingularAttribute<Supervision, Date> startDate;
	public static volatile SingularAttribute<Supervision, Date> endDate;
}