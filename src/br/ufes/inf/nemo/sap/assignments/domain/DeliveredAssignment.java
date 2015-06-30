package br.ufes.inf.nemo.sap.assignments.domain;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the deliveredAssignments.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Entity
public class DeliveredAssignment extends PersistentObjectSupport implements Comparable<DeliveredAssignment> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** Path where the file sent this saved. */
	@Basic
	@NotNull
	private String file;
	
	/** Date that the assignment was sent. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date deliveryDate;
	
	/** Md5 number generated at the time of sending the assignment. */
	@Basic
	@NotNull
	private String md5;
	
	/** Assignment group associated with the delivered assignment. */
	@ManyToOne
	private AssignmentGroup assignmentGroup;

	/** Getter for file. */
	public String getFile() {
		return file;
	}

	/** Setter for file. */
	public void setFile(String file) {
		this.file = file;
	}

	/** Getter for deliveryDate. */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/** Setter for deliveryDate. */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/** Getter for md5. */
	public String getMd5() {
		return md5;
	}

	/** Setter for md5. */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/** Getter for assignmentGroup. */
	public AssignmentGroup getAssignmentGroup() {
		return assignmentGroup;
	}

	/** Setter for assignmentGroup. */
	public void setAssignmentGroup(AssignmentGroup assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	/** The nemo-utils mini CRUD framework requires that classes managed by it be comparable for sorting. */
	@Override
	public int compareTo(DeliveredAssignment o) {
		return 0;
	}
}