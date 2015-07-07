package br.ufes.inf.nemo.sap.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Filter used in the pages of requests to verify that the session has expired and redirect to the error page.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

public class ErrorPageFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
					throws IOException, ServletException {   	
	    /** Get the session. */
		HttpServletRequest hreq = (HttpServletRequest)request;
	    HttpSession session = hreq.getSession();

	    /** Get the Request URL. */
	    String url = hreq.getRequestURI().toString();	    
	    	    
	    /** Get the user of the Session. */
	    String user = (String) session.getAttribute("user");
	    
	    /** Verifies that the session has expired and disregards the pages that do not need to login. */ 
	    if	(	
		    	(user == null)	    	
		    	&& (url.indexOf("/SAP/index.html") == -1)
		    	&& (url.indexOf("/SAP/resources/") == -1)
		    	&& (url.indexOf("/SAP/index.faces") == -1)
		    	&& (url.indexOf("/SAP/javax.faces.") == -1)
		    	&& (url.indexOf("/SAP/indexStudent.faces") == -1)
		    	&& (url.indexOf("/SAP/indexProfessor.faces") == -1)
		    	&& (url.indexOf("/SAP/login/loginStudent.faces") == -1)
		    	&& (url.indexOf("/SAP/login/loginProfessor.faces") == -1)
	    	) {
	    	
	    	/** Redirects to the error page. */
	    	hreq.getRequestDispatcher("/errorPage.faces").forward(request, response);	    	
	    }
	    else{
	    	chain.doFilter(request, response);
	    }
	}
	
	@Override
	public void destroy() {}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}