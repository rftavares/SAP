package br.ufes.inf.nemo.sap.assignments.application;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.ufes.inf.nemo.util.TextUtils;

/**
 * Singleton bean that stores in memory information that is useful for the entire application, i.e., 
 * read-only information shared by all users. This bean stores information for the assignment package.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

@Singleton
@Named("sapInfo")
public class SAPInformation implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Indicates the decorator being used in the administration area. */
	private String decorator = "default";

	/** Getter for decorator. */
	public String getDecorator() {
		/** Get the session. */
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	    
	    if(session.getAttribute("user") == null){
	    	decorator = "default";
	    }
	    
		return decorator;
	}
	
	/** Setter for decorator. */
	public String setDecorator(String decorator) {
		return this.decorator = decorator;
	}
	
	/**************************************************************************************************
	 * Method used to transform a string into md5 hash.
	 * 
	 * @param str
	 *      String with the password.   
	 * 
	 * @return
	 * 		String with the password transformed into MD5 hash.
	 ***************************************************************************************************/	
	public String transformStringToMd5Hash(String str){		
		String md5pwd = "";
		
		try {
			md5pwd = TextUtils.produceMd5Hash(str);			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5pwd;
	}
	
	/**************************************************************************************************
	 * Method used to calculate the difference of days between two dates.
	 * 
	 * @param startDate
	 *      Initial date.
	 *      
	 * @param endDate
	 *      Final date.
	 * 
	 * @return
	 * 		The number of days the difference.
	 ***************************************************************************************************/	
	public double calculateDifferenceDays(Date startDate, Date endDate){
        long diffMilliSeconds = endDate.getTime() - startDate.getTime();  
        double diffDays = (diffMilliSeconds /1000) / 60 / 60 /24;  
          
        return diffDays;  
    }
}