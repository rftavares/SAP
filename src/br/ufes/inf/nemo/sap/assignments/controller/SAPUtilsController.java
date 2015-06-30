package br.ufes.inf.nemo.sap.assignments.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.util.ejb3.controller.JSFController;


/**
 * Session-scoped managed bean that provides to web pages the login service, indication if the user is logged in, the
 * user's menu and the current date/time.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@Named
@SessionScoped
public class SAPUtilsController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;	
	
	/**************************************************************************************************
	 * Method used to display a message on the screen.
	 * 
	 * @param severity
	 *      Used to indicate the level of severity of the message icon.
	 *      
	 * @param keyMessage
	 *      String with the message key.
	 * 
	 * @return
	 * 		The number of days the difference.
	 ***************************************************************************************************/
	public void showGlobalMessage(FacesMessage.Severity severity, String keyMessage) {
		addGlobalI18nMessage("msgs", severity, keyMessage, "");
	}
}