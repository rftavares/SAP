package br.ufes.inf.nemo.sap.assignments.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufes.inf.nemo.sap.assignments.application.*;
import br.ufes.inf.nemo.sap.assignments.domain.*;
import br.ufes.inf.nemo.util.ejb3.controller.JSFController;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.*;

/**
 * Session-scoped managed bean that provides to web pages the login service, indication if the user is logged in, the
 * user's menu and the current date/time.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@Named
@SessionScoped
public class SessionController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Information on the whole application. */
	@EJB
	private SAPInformation sapInformation;

	/** Information on the current visitor. */
	@EJB
	private SessionInformation sessionInformation;

	/** Input: e-mail for authentication. */
	private String email;
	
	/** Input: enrollment for authentication. */
	private String enrollment;

	/** Input: password for authentication. */
	private String password;
	
	/** The current professor logged in. */
	private Professor currentProfessor;
	
	/** The current professor logged in. */
	private Student currentStudent;
	
	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/** Getter for enrollment. */
	public String getEnrollment() {
		return enrollment;
	}

	/** Setter for enrollment. */
	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}
	
	/** Getter for current professor logged in. */
	public Professor getCurrentProfessor() {
		return currentProfessor;
	}

	/** Setter for current professor logged in. */
	public void setCurrentProfessor(Professor currentProfessor) {
		this.currentProfessor = currentProfessor;
	}
	
	/** Getter for student logged in. */
	public Student getCurrentStudent() {
		return currentStudent;
	}

	/** Setter for student logged in. */
	public void setCurrentStudent(Student currentStudent) {
		this.currentStudent = currentStudent;
	}
	
	/** Type of logged in User. Used in the index pages. */
	private String typeUser;

	/** Getter for typeUser. */
	public String getTypeUser() {
		return typeUser;
	}

	/** Setter for typeUser. */
	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}

	/**************************************************************************************************
	 * Method used to authenticate professor.
	 * 
	 * @return
	 * 		On success, redirects to the homepage professor, otherwise displays an error message on the screen. 
	 ***************************************************************************************************/
	/** Accesses the Professor Login service to authenticate the user given his email and password. */
	public String loginProfessor() throws 	PersistentObjectNotFoundException, 
											MultiplePersistentObjectsFoundException {		
		/** Uses the Login service to authenticate the user. */			
		currentProfessor = sessionInformation.loginProfessor(email, password);
		
		if (currentProfessor != null) {
			if(currentProfessor.getAdministrator() == AdministratorEnum.YES) {
				/** Changes to the decorator to the administrator's menu. */
				sapInformation.setDecorator("administrator");
			}
			else{
				/** Changes to the decorator to the professor's menu. */
				sapInformation.setDecorator("professor");
			}
			
			/** Sets the type of user. */
			typeUser = "Professor";
			
			/** Add user in the session. */
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", currentProfessor.getName());
			session.setAttribute("professor", currentProfessor);
			
			/** Returns to the administrator / professor homepage. */
			return "/indexProfessor.faces?faces-redirect=true";
		}
		else {
			/** Displays on the screen error message at login. */
			addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_INFO, "login.professor.messageError", "");
			return "";
		}		
	}
	
	/**************************************************************************************************
	 * Method used to authenticate professor.
	 * 
	 * @return
	 * 		On success, redirects to the homepage professor, otherwise displays an error message on the screen. 
	 ***************************************************************************************************/
	public String loginStudent() throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {		
		/** Uses the Login service to authenticate the user. */			
		currentStudent = sessionInformation.loginStudent(enrollment, password);
				
		/** Verifies that the login is correct. */
		if (currentStudent != null) {
			/** Changes to the decorator to the student's menu. */
			sapInformation.setDecorator("student");
			
			/** Sets the type of user. */
			typeUser = "Student";
			
			/** Add user in the session. */
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", currentStudent.getName());
			session.setAttribute("student", currentStudent);
						
			/** Returns to the student homepage. */
			return "/indexStudent.faces?faces-redirect=true";
		}
		else {
			/** Displays on the screen error message at login. */
			addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_WARN, "login.student.messageError", "");
			return "";
		}		
	}

	/**************************************************************************************************
	 * Method used to method used to make the logoff, change the decorator and clear the session data.
	 * 
	 * @return
	 * 		Redirects to the homepage. 
	 ***************************************************************************************************/
	/** Logout and back to the home page. */
	public String logout() throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		/** Changes to the decorator to the default menu. */
		sapInformation.setDecorator("default");
		
		/** Clean the login data. */
		password = "";
		email = "";
		typeUser = "";
		enrollment = "";
		currentProfessor = null;
		currentStudent = null;
		
		/** Remove user in the session. */
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("user");
		session.removeAttribute("professor");
		session.removeAttribute("student");
						
		/** Returns to the home page. */
		return "/index.faces?faces-redirect=true";		
	}
	
	/**************************************************************************************************
	 * Method used to change the professor login data and return to the user home page.
	 * 
	 * @return
	 * 		On success, redirects to the homepage professor, otherwise displays an error message on the screen. 
	 ***************************************************************************************************/	
	public String loginProfessorAlterData() {
		Long oldVersion = currentProfessor.getVersion();
		
		/** Try to update professor data. */
		String errorMessageUpdate = sessionInformation.updateProfessor(currentProfessor);
		
		currentProfessor = sessionInformation.getCurrentProfessor();
		Long newVersion = currentProfessor.getVersion();
		
		/** Checks if any error occurred while trying to update the data. */
		if((oldVersion == newVersion) && (! errorMessageUpdate.isEmpty())){
			addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_WARN, errorMessageUpdate, "");
			return "";
		}			
		else{
			/** Add user in the session. */
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", currentProfessor.getName());
			session.setAttribute("professor", currentProfessor);
			
			/** Changed data with sucess, back to Home Page. */
			return returnToIndexPage();
		}
	}
	
	/**************************************************************************************************
	 * Method used to change the student login data and return to the user home page.
	 * 
	 * @return
	 * 		On success, redirects to the homepage student, otherwise displays an error message on the screen. 
	 ***************************************************************************************************/	
	public String loginStudentAlterData() {
		Long oldVersion = currentStudent.getVersion();
		
		/** Try to update professor data. */
		String errorMessageUpdate = sessionInformation.updateStudent(currentStudent);
		
		currentStudent = sessionInformation.getCurrentStudent();
		Long newVersion = currentStudent.getVersion();
		
		/** Checks if any error occurred while trying to update the data. */
		if((oldVersion == newVersion) && (! errorMessageUpdate.isEmpty())){
			addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_WARN, errorMessageUpdate, "");
			return "";
		}			
		else{
			/** Changed data with sucess, back to Home Page. */
			return returnToIndexPage();
		}
	}
	
	/**************************************************************************************************
	 * Method used to return the user homepage.
	 * 
	 * @return
	 * 		User homepage. 
	 ***************************************************************************************************/	
	public String returnToIndexPage() {
		/** If the session has expired. */
		if(typeUser == null){
			typeUser = "";
		}
		
		return "/index" + typeUser + ".faces?faces-redirect=true";		
	}
}