package br.ufes.inf.nemo.sap.assignments.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the students.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class Student extends PersistentObjectSupport implements Comparable<Student> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Name of the student. */
	@Basic
	@NotNull
	private String name;
	
	@Basic
	@NotNull
	private String enrollment;
	
	/** Email of the student. */
	@Basic
	private String email;
	
	/** Phone of the student. */
	@Basic
	private String phone;

	/** Password to access the system. */
	@Basic
	private String password;
			
	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Getter for enrollment. */
	public String getEnrollment() {
		return enrollment;
	}

	/** Getter for enrollment. */
	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for phone. */
	public String getPhone() {
		return phone;
	}

	/** Setter for phone. */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}
		
	/** Representation of class in text form. */
	@Override
	public String toString() {
		return name;
	}
	
	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(Student o) {
		/** name */
		int cmp = name.compareTo(o.name);
		if (cmp != 0)
			return cmp;
		
		/** enrollment */
		cmp = enrollment.compareTo(o.enrollment);
		if (cmp != 0)
			return cmp;

		/** If it's the same name and enrollment, check if it's the same entity. */
		return uuid.compareTo(o.uuid);
	}
}