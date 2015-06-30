package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the courses.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class Course extends PersistentObjectSupport implements Comparable<Course> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Name of the course. */
	@Basic
	@NotNull
	private String name;
	
	/** Code of the course. */
	@Basic
	@NotNull
	private String code;
	
	/** Schoolrooms related to the course. */
	@OneToMany(mappedBy = "course")
	private Set<SchoolRoom> schoolRooms;

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for code. */
	public String getCode() {
		return code;
	}

	/** Setter for code. */
	public void setCode(String code) {
		this.code = code;
	}
	
	/** Getter for schoolRooms. */
	public Set<SchoolRoom> getSchoolRooms() {
		return schoolRooms;
	}

	/** Setter for schoolRooms. */
	public void setSchoolRooms(Set<SchoolRoom> schoolRooms) {
		this.schoolRooms = schoolRooms;
	}
	
	/** Representation of class in text form. */
	@Override
	public String toString() {
		return code + " - " + name;
	}

	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */	
	@Override
	public int compareTo(Course o) {
		return 0;
	}
}