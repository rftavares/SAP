package br.ufes.inf.nemo.sap.assignments.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.sap.assignments.persistence.*;
import br.ufes.inf.nemo.sap.lab.domain.ResearchGroup;
import br.ufes.inf.nemo.sap.lab.domain.persistence.ResearchGroupDAO;
import br.ufes.inf.nemo.util.ejb3.controller.PersistentObjectConverterFromId;

/**
 * Application-scoped bean that centralizes controller information for the assignment package. This bean differs from the
 * singleton EJB Assignment by containing data relative to the presentation layer (controller and view, i.e., the web).
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Named
@ApplicationScoped
public class AssignmentController implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The DAO for SchoolRoom objects. */
	@EJB
	private SchoolRoomDAO schoolRoomDAO;
	
	/** The DAO for Course objects. */
	@EJB
	private CourseDAO courseDAO;
	
	/** The DAO for Discipline objects. */
	@EJB
	private DisciplineDAO disciplineDAO;
	
	/** The DAO for Period objects. */
	@EJB
	private PeriodDAO periodDAO;

	/** The DAO for Professor objects. */
	@EJB
	private ProfessorDAO professorDAO;
	
	/** The DAO for Student objects. */
	@EJB
	private StudentDAO studentDAO;
	
	/** The DAO for Assignment objects. */
	@EJB
	private AssignmentDAO assignmentDAO;
	
	/** The DAO for ResearchGroup objects. */
	@EJB
	private ResearchGroupDAO researchGroupDAO;
	
	/** JSF Converter for Course objects. */
	private PersistentObjectConverterFromId<Course> courseConverter;
	
	/** JSF Converter for Course objects. */
	private PersistentObjectConverterFromId<Discipline> disciplineConverter;
	
	/** JSF Converter for Period objects. */
	private PersistentObjectConverterFromId<Period> periodConverter;
	
	/** JSF Converter for Professor objects. */
	private PersistentObjectConverterFromId<Professor> professorConverter;
	
	/** JSF Converter for Student objects. */
	private PersistentObjectConverterFromId<Student> studentConverter;
	
	/** JSF Converter for SchoolRoom objects. */
	private PersistentObjectConverterFromId<SchoolRoom> schoolRoomConverter;
	
	/** JSF Converter for Assignment objects. */
	private PersistentObjectConverterFromId<Assignment> assignmentConverter;
	
	/** JSF Converter for ResearchGroup objects. */
	private PersistentObjectConverterFromId<ResearchGroup> researchGroupConverter;
	
	/** Getter for professor converter. */
	public Converter getProfessorConverter() {
		if (professorConverter == null) {
			professorConverter = new PersistentObjectConverterFromId<Professor>(professorDAO);
		}
		return professorConverter;
	}
	
	/** Getter for student converter. */
	public Converter getStudentConverter() {
		if (studentConverter == null) {
			studentConverter = new PersistentObjectConverterFromId<Student>(studentDAO);
		}
		return studentConverter;
	}
	
	/** Getter for period converter. */
	public Converter getPeriodConverter() {
		if (periodConverter == null) {
			periodConverter = new PersistentObjectConverterFromId<Period>(periodDAO);
		}
		return periodConverter;
	}	
	
	/** Getter for discipline converter. */
	public Converter getDisciplineConverter() {
		if (disciplineConverter == null) {
			disciplineConverter = new PersistentObjectConverterFromId<Discipline>(disciplineDAO);
		}
		return disciplineConverter;
	}	
	
	/** Getter for course converter. */
	public Converter getCourseConverter() {
		if (courseConverter == null) {
			courseConverter = new PersistentObjectConverterFromId<Course>(courseDAO);
		}
		return courseConverter;
	}
	
	/** Getter for schoolRoom converter. */
	public Converter getSchoolRoomConverter() {
		if (schoolRoomConverter == null) {
			schoolRoomConverter = new PersistentObjectConverterFromId<SchoolRoom>(schoolRoomDAO);
		}
		return schoolRoomConverter;
	}
	
	/** Getter for Assignment converter. */
	public Converter getAssignmentConverter() {
		if (assignmentConverter == null) {
			assignmentConverter = new PersistentObjectConverterFromId<Assignment>(assignmentDAO);
		}
		return assignmentConverter;
	}
	
	/** Getter for ResearchGroup converter. */
	public Converter getResearchGroupConverter() {
		if (researchGroupConverter == null) {
			researchGroupConverter = new PersistentObjectConverterFromId<ResearchGroup>(researchGroupDAO);
		}
		return researchGroupConverter;
	}
}