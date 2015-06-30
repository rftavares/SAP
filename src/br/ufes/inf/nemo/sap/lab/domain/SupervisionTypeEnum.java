package br.ufes.inf.nemo.sap.lab.domain;

/**
 * Enum that identifies the types of supervision.
 * 
 * @author Luiz Vitor Franca Lima / Worlen Augusto Gomes
 */

public enum SupervisionTypeEnum {
	INITIATIONSCIENTIFIC("INITIATIONSCIENTIFIC"), 
	GRADUATION("GRADUATION"), 
	MASTER("MASTER"), 
	DOCTORATE("DOCTORATE");
	
	public String type;
	
	private SupervisionTypeEnum(String type) { 
		this.type = type; 
	}
	
	public String getType() {
		return type;
	}
}