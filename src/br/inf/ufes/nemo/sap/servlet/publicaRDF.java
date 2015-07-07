package br.inf.ufes.nemo.sap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufes.inf.nemo.sap.lab.domain.ResearchGroup;
import br.ufes.inf.nemo.sap.lab.domain.persistence.ResearchGroupDAO;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

@WebServlet(urlPatterns = { "/data/ResearchGroupsRDF" })
public class publicaRDF  extends HttpServlet{
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ResearchGroupDAO researchGroupDAO;
	
	private static final DateFormat df = new SimpleDateFormat ("dd-MM-yyyy");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Escolhendo a saída para xml
		response.setContentType("text/xml");
		
		List <ResearchGroup> researchGroups = researchGroupDAO.retrieveAll();
		
		
		Model model = ModelFactory.createDefaultModel();
		
		String myNS = "http://localhost:8080/SAP/data/ResearchGroupRDF";
		String dsNS = "http://purl.org/goodrelations/v1#";
		model.setNsPrefix("ds", dsNS);
		
		Resource dsName = ResourceFactory.createResource(dsNS + "Name");
		
		
		Property site = ResourceFactory.createProperty(dsNS +
		"Site");
		Property professor = ResourceFactory.createProperty(dsNS + "Professores");
		for (ResearchGroup rg: researchGroups){
			Resource ResearchGroupRDF = model.createResource(myNS + rg.getId());
			
			ResearchGroupRDF.addProperty(RDF.type, dsName);
			ResearchGroupRDF.addLiteral(site, rg.getSite());
			ResearchGroupRDF.addLiteral(professor, rg.getProfessors().toString());
		}
		try (PrintWriter out = response.getWriter()){
			model.write(out, "RDF/XML");			
		}
		
	}
	
	
	

}