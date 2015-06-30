package br.ufes.inf.nemo.sap.assignments.domain;

import javax.persistence.metamodel.*;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Student domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@StaticMetamodel(Student.class)
public class Student_ extends PersistentObjectSupport_ {	
	public static volatile SingularAttribute<Student, String> name;
	public static volatile SingularAttribute<Student, String> email;
	public static volatile SingularAttribute<Student, String> password;
	public static volatile SingularAttribute<Student, String> phone;
	public static volatile SingularAttribute<Student, String> enrollment;
}