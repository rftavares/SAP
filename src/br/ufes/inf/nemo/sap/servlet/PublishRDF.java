package br.ufes.inf.nemo.sap.servlet;

import java.io.*;
import java.util.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import br.ufes.inf.nemo.sap.lab.domain.*;
import br.ufes.inf.nemo.sap.lab.domain.persistence.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.RDF;

@WebServlet(urlPatterns = { "/data/ResearchGroupsRDF" })
public class PublishRDF  extends HttpServlet{
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ResearchGroupDAO researchGroupDAO;
	
	// private static final DateFormat df = new SimpleDateFormat ("dd-MM-yyyy");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Escolhendo a saída para xml
		response.setContentType("text/xml");
		
		List <ResearchGroup> researchGroups = researchGroupDAO.retrieveAll();		
		
		Model model = ModelFactory.createDefaultModel();
		
		String myNS = "http://localhost:8080/SAP/data/ResearchGroupRDF";
		String dsNS = "http://www.ncd.ufes.br/v1#";
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