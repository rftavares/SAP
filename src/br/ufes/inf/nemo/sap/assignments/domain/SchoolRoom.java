package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the schoolRooms.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class SchoolRoom extends PersistentObjectSupport implements Comparable<SchoolRoom> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Number of the schoolroom. */
	@Basic
	@NotNull
	private String number;

	/** Period associated with the schoolroom. */
	@ManyToOne
	private Period period;
	
	/** Discipline associated with the schoolroom. */
	@ManyToOne
	private Discipline discipline;
	
	/** Professor associated with the schoolroom. */
	@ManyToOne
	private Professor professor;
	
	/** Course associated with the schoolroom. */
	@ManyToOne
	private Course course;
	
	/** Assignment related to the schoolroom. */
	@OneToMany(mappedBy = "schoolRoom")
	private Set<Assignment> assignments;

	/** Getter for number. */
	public String getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(String number) {
		this.number = number;
	}

	/** Getter for period. */
	public Period getPeriod() {
		return period;
	}

	/** Setter for period. */
	public void setPeriod(Period period) {
		this.period = period;
	}

	/** Getter for discipline. */
	public Discipline getDiscipline() {
		return discipline;
	}

	/** Setter for discipline. */
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	/** Getter for professor. */
	public Professor getProfessor() {
		return professor;
	}

	/** Setter for professor. */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	/** Getter for course. */
	public Course getCourse() {
		return course;
	}

	/** Setter for course. */
	public void setCourse(Course course) {
		this.course = course;
	}
	
	/** Students of the schoolRoom. */	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Student> students;
	
	/** Getter for students. */
	public Set<Student> getStudents() {
		return students;
	}

	/** Setter for students. */
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	/** Representation of class in text form. */
	@Override
	public String toString() {
		return number + " - "  + discipline.toString();
	}

	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(SchoolRoom o) {
		return 0;
	}
}