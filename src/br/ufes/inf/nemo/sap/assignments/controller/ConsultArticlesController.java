package br.ufes.inf.nemo.sap.assignments.controller;


import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.util.ejb3.controller.JSFController;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

/**
 * Session-scoped managed bean that provides to web pages the login service, indication if the user is logged in, the
 * user's menu and the current date/time.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */
@Named
@SessionScoped
public class ConsultArticlesController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** . */
	private String nameAuthorField;
    
	/** Getter for nameAuthorField. */
	public String getNameAuthorField() {
		return nameAuthorField;
	}

	/** Setter for nameAuthorField. */
	public void setNameAuthorField(String nameAuthorField) {
		this.nameAuthorField = nameAuthorField;
	}
	
	private List<String> tituloPublicacao = new ArrayList<String>();
	

	public List<String> getTituloPublicacao() {
		return tituloPublicacao;
	}

	public void setTituloPublicacao(List<String> tituloPublicacao) {
		this.tituloPublicacao = tituloPublicacao;
	}

	public void teste(){
		//String name = this.getName();
		String title = "";
		String service = "http://dblp.rkbexplorer.com/sparql";
		String query = "PREFIX akt:  <http://www.aktors.org/ontology/portal#> "
		+"PREFIX akt:  <http://www.aktors.org/ontology/portal#> "+
				" SELECT DISTINCT ?name ?title "+
		" WHERE "+
				" { "+
		" ?auth akt:full-name "+ nameAuthorField +
		" . "+
		" ?pub a akt:Publication-Reference ;"+
		" akt:has-title ?title ;"+
		" akt:has-author ?auth ."+
		" } ";
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(service, query); 
		try {
		 if(queryExecution.execAsk()){
		     //UP
		     ResultSet results = queryExecution.execSelect();
		     while (results.hasNext()){
		         QuerySolution querySolution = results.nextSolution();
		         //consume title
		         Literal literal = querySolution.getLiteral("title");
		         title=("" + literal.getValue());
		         
		         //add titulo a lista tituloPublicacao
		         this.tituloPublicacao.add(title);
		   }
		}
		  } catch(QueryExceptionHTTP e) {
			System.out.println(service + " is DOWN");
		  } finally { queryExecution.close(); }
	}
}