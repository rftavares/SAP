package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the periods.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class Period extends PersistentObjectSupport implements Comparable<Period> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Year of the period. */
	@Basic
	@NotNull
	private String year;
	
	/** Number of the period. */
	@Basic
	@NotNull
	private String number;
	
	/** Starting date of the period. */
	@Temporal(TemporalType.DATE)
	
	private Date startDate;
	
	/** The end date of the period. */
	@Temporal(TemporalType.DATE)
	
	private Date endDate;
	
	/** Schoolrooms related to the period. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "period")
	private Set<SchoolRoom> schoolRooms;
	
	/** Getter for year. */
	public String getYear() {
		return year;
	}
	
	/** Setter for year. */
	public void setYear(String year) {
		this.year = year;
	}
	
	/** Getter for number. */
	public String getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(String number) {
		this.number = number;
	}

	/** Getter for startDate. */
	public Date getStartDate() {
		return startDate;
	}

	/** Setter for startDate. */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/** Getter for endDate. */
	public Date getEndDate() {
		return endDate;
	}

	/** Setter for endDate. */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
		return year + "/" + number;
	}
	
	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(Period o) {
		return 0;
	}
}