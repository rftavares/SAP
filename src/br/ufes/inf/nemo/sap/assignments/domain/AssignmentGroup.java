package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the AssignmentGroups.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class AssignmentGroup extends PersistentObjectSupport implements Comparable<AssignmentGroup> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Number of the group. */
	@Basic
	@NotNull
	private String number;
	
	/** Assignment associated with the group. */
	@ManyToOne
	private Assignment assignment;
	
	/** Students of the assignmentGroup. */
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Student> students;
	
	/** Delivered assignments related to the assignment Group. */
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "assignmentGroup")
	//private Set<DeliveredAssignment> deliveredAssignment;

	/** Getter for number. */
	public String getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(String number) {
		this.number = number;
	}

	/** Getter for assignment. */
	public Assignment getAssignment() {
		return assignment;
	}
	
	/** Setter for assignment. */
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	/** Getter for students. */
	public Set<Student> getStudents() {
		return students;
	}

	/** Setter for students. */
	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	/** Getter for deliveredAssignment. */
	//public Set<DeliveredAssignment> getDeliveredAssignment() {
	//	return deliveredAssignment;
	//}

	/** Setter for deliveredAssignment. */
	//public void setDeliveredAssignment(Set<DeliveredAssignment> deliveredAssignment) {
	//	this.deliveredAssignment = deliveredAssignment;
	//}

	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(AssignmentGroup o) {
		return 0;
	}
}