package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the professors.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@Table(name="professor")
@Entity
public class Professor extends PersistentObjectSupport implements Comparable<Professor> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Name of the professor. */
	@Basic
	@NotNull
	private String name;
	
	/** Email of the professor. */
	@Basic
	@NotNull
	private String email;
	
	/** Identifies whether the professor is administrator. */
	@Basic
	@NotNull
	private AdministratorEnum administrator;
	
	/** Password to access the system. */
	@Basic
	private String password;
	
	/** Schoolrooms related to the professor. */
	@OneToMany(mappedBy = "professor")
	private Set<SchoolRoom> schoolRooms;
	
	/** Supervision related to the advisor. */
	@OneToMany(mappedBy = "advisor")
	private Set<Supervision> supervisions;
	
	/** Supervision related to the coadvisor. */
	@OneToMany(mappedBy = "advisor")
	private Set<Supervision> coadvisor;
		
	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/** Getter for administrator. */
	public AdministratorEnum getAdministrator() {
		return administrator;
	}
	
	/** Setter for administrator. */
	public void setAdministrator(AdministratorEnum administrator) {
		this.administrator = administrator;
	}
	
	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/** Getter for schoolRooms. */
	public Set<SchoolRoom> getSchoolRooms() {
		return schoolRooms;
	}

	/** Setter for schoolRooms. */
	public void setSchoolRooms(Set<SchoolRoom> schoolRooms) {
		this.schoolRooms = schoolRooms;
	}

	/** Getter for supervision. */
	public Set<Supervision> getSupervisions() {
		return supervisions;
	}

	/** Setter for supervision. */
	public void setSupervisions(Set<Supervision> supervisions) {
		this.supervisions = supervisions;
	}

	/** Getter for coadvisor. */
	public Set<Supervision> getCoadvisor() {
		return coadvisor;
	}

	/** Setter for coadvisor. */
	public void setCoadvisor(Set<Supervision> coadvisor) {
		this.coadvisor = coadvisor;
	}
	
	/** Representation of class in text form. */
	@Override
	public String toString() {
		return name;
	}
	
	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(Professor o) {
		return 0;
	}
}