package br.ufes.inf.nemo.sap.lab.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the ResearchGroup.
 *  
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class ResearchGroup extends PersistentObjectSupport implements Comparable<ResearchGroup> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Name of the research group. */
	@Basic
	@NotNull
	private String name;
	
	/** The research group's web page address. */
	@Basic
	private String site;
	
	/** Professors related to the research group. */
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Professor> professors;

	/** Getter for name. */
	public String getName() {
		return name;
	}
	
	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for site. */
	public String getSite() {
		return site;
	}

	/** Setter for site. */
	public void setSite(String site) {
		this.site = site;
	}
	
	/** Getter for professors. */
	public Set<Professor> getProfessors() {
		return professors;
	}

	/** Setter for professors. */
	public void setProfessors(Set<Professor> professors) {
		this.professors = professors;
	}
	
	/** Representation of class in text form. */
	@Override
	public String toString() {
		return name;
	}

	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(ResearchGroup o) {
		return 0;
	}
}