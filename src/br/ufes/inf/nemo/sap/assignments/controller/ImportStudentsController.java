package br.ufes.inf.nemo.sap.assignments.controller;

import java.io.*;
import java.util.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufes.inf.nemo.sap.assignments.application.*;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.controller.JSFController;

/**
 * Session-scoped managed bean that provides to web pages the login service, indication if the user is logged in, the
 * user's menu and the current date/time.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@Named
@SessionScoped
public class ImportStudentsController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The "Manage SchoolRooms" service. */
	@EJB
	private ManageSchoolRoomsService manageSchoolRoomsService;
	
	/** . */
	@EJB
	private ImportStudentInformation importStudentInformation;

	/** Period variable used to store the current value of the entity on the page. */
	private Period periodField;
	
	/** Getter for periodField. */
	public Period getPeriodField() {
		return periodField;
	}

	 /** Setter for periodField. */
	public void setPeriodField(Period periodField) {
		this.periodField = periodField;
	}

	/** Schoolroom variable used to store the current value of the entity on the page. */
	private SchoolRoom schoolRoomField;
    
	/** Getter for schoolRoomField. */
    public SchoolRoom getSchoolRoomField() {
		return schoolRoomField;
	}

    /** Setter for schoolRoomField. */
	public void setSchoolRoomField(SchoolRoom schoolRoomField) {
		this.schoolRoomField = schoolRoomField;
	}
	
	/** List used to display the schoolRooms on the page. */
	private List<SchoolRoom> schoolRooms;

	/** Getter for schoolRooms. */
	public List<SchoolRoom> getSchoolRooms() {
		return schoolRooms;
	}

	/** Setter for schoolRooms. */
	public void setSchoolRooms(List<SchoolRoom> schoolRooms) {
		this.schoolRooms = schoolRooms;
	}
	
	/** Method used to return a list of the schoolRoom of a selected period. */
	public void loadSchoolRooms(AjaxBehaviorEvent event) {
		/** Returns the component that triggered the event ajax. */
		Map<String, Object> map = event.getComponent().getAttributes();
		Period periodEntity = (Period) map.get("value");
		
		/** Clean the list data, if exists. */
		if(schoolRooms != null) {
			schoolRooms.clear();
			schoolRoomField = null;
		}
		
		/** Get the session. */
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	    
	    /** Get the professor logged. */
	    Professor professor = (Professor) session.getAttribute("professor");
	    
	    /** Checks whether the professor is administrator. */
	    if(professor.getAdministrator() == AdministratorEnum.YES){
	    	/** Loads all classes of the selected period in the list. */
	    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(periodEntity);
	    }
	    else{
	    	/** Loads in the list all schoolRooms of the selected period and professor. */
	    	schoolRooms = manageSchoolRoomsService.getSchoolRooms(periodEntity, professor);
	    }
	}
	
	// UTILIZADO PARA EXIBIR O RESULTADO DA IMPORTACAO
	List<ResultImportStudent> listStudentsResultImport = null;
	
	public List<ResultImportStudent> getListStudentsResultImport() {
		return listStudentsResultImport;
	}

	public void setListStudentsResultImport(List<ResultImportStudent> listStudentsResultImport) {
		this.listStudentsResultImport = listStudentsResultImport;
	}

	public void save(){
		/** Declares the variables to the file reading. */
		// VERIFICAR ESSA QUESTAO DO CAMINHO DEPOIS
		String fileCSV = "C:\\arquivo.csv";
	    BufferedReader fileReader = null;
	    String line = "";
	    String csvDivisor = ";";
	    
	    try {
	    	/** Read the first line. */
	    	fileReader = new BufferedReader(new FileReader(fileCSV));
	        
	        /** Read the first line (header). */
	        line = fileReader.readLine();
	        
	        List<Student> studentsSchoolRoom = new ArrayList<Student>(schoolRoomField.getStudents());
	        
	        listStudentsResultImport = new ArrayList<ResultImportStudent>();
	        
	        /** Reads lines of the file. */
	        while ((line = fileReader.readLine()) != null) {
	            String[] vectorLine = line.split(csvDivisor);
	            //System.out.println(vectorLine[0] + " - " + vectorLine[1] + " - " + vectorLine[2] + " - " + vectorLine[3]);
	            
	            /** Stores the file line data. */
	            String enrollment = vectorLine[2];
	            String name = vectorLine[3];
	            
	            /** Verifies that the student is already registered in the system. */
	            Student student = null;
	            
	            student = importStudentInformation.retrieveStudent(enrollment);
	            
			    if(student == null){
			    	/** Creates a new entity for the student. */
			    	Student newStudent = new Student();
			    	newStudent.setName(name);
			    	newStudent.setEnrollment(enrollment);
			    	
			    	/** Password default: 123456 */
			    	newStudent.setPassword("e10adc3949ba59abbe56e057f20f883e");
			    	
			    	/** Inserts the new entity in the database. */
			    	newStudent = importStudentInformation.saveStudent(newStudent);
			    	
			    	// ADICIONA NA LISTA DE RESULTADO.
			    	ResultImportStudent newResult = new ResultImportStudent(newStudent, 1);
			    	listStudentsResultImport.add(newResult);
			    	
			    	/** Checks whether any errors occurred when inserting the new entity in the database. */
			    	if(newStudent == null) {
			    		listStudentsResultImport = null;
			    		addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_FATAL, "importStudents.form.student.database.error", "");
			    		return;
			    	}
			    	else {
			    		/** Inserts the student in schoolRoom. */
				    	studentsSchoolRoom.add(newStudent);
			    	}
			    } else {
			    	/** Verifies that the student not in this schoolRoom. */
			    	if(! studentsSchoolRoom.contains(student)){
			    		/** Inserts the student in schoolRoom. */
				    	studentsSchoolRoom.add(student);
				    	
				    	// ADICIONA NA LISTA DE RESULTADO.
				    	ResultImportStudent newResult = new ResultImportStudent(student, 2);
				    	listStudentsResultImport.add(newResult);
			    	}
			    	else{
			    		// ADICIONA NA LISTA DE RESULTADO.
			    		ResultImportStudent newResult = new ResultImportStudent(student, 3);
				    	listStudentsResultImport.add(newResult);
			    		
			    		/** The student is already in the schoolRoom. Go to the next iteration of the loop. */
			    		continue;
			    	}
			    }
	        }
	        
	        /** Updates the entity and the database with new data. */
	        schoolRoomField.setStudents(new HashSet<Student>(studentsSchoolRoom));
	        String errorMessageUpdate = importStudentInformation.updateSchoolRoom(schoolRoomField);
	        
	        /** Checks whether any errors occurred while attempting to update the database. */
	        if(! errorMessageUpdate.equals("")){
	        	listStudentsResultImport = null;
	        	addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_FATAL, errorMessageUpdate, "");
	        }
	    } 
	    catch (FileNotFoundException e) {
	    	listStudentsResultImport = null;
	    	addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_FATAL, "importStudents.form.archive.notfound", "");
	        e.printStackTrace();
	    } 
	    catch (IOException e) {
	    	listStudentsResultImport = null;
	    	addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_FATAL, "importStudents.form.archive.error", "");
	        e.printStackTrace();
	    } 
	    finally {
	    	/** Verifies that not occurred some error. */
	        if (fileReader != null) {
	            try {
	            	addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_INFO, "importStudents.form.archive.success", "");
	            	fileReader.close();
	            } 
	            catch (IOException e) {
	            	listStudentsResultImport = null;
	            	addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_FATAL, "importStudents.form.archive.error", "");
	                e.printStackTrace();	                
	            }
	        }
	    }
	}
}