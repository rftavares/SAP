package br.ufes.inf.nemo.sap.assignments.domain;

/**
 * Enum that identifies if a professor is administrator.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

public enum AdministratorEnum {
	NO("NO"), 
	YES("YES");
	
	public String type;
	
	private AdministratorEnum(String type) { 
		this.type = type; 
	}
	
	public String getAdministrator() {
		return type;
	}	
}