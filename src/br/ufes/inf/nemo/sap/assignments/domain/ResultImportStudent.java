package br.ufes.inf.nemo.sap.assignments.domain;

public class ResultImportStudent {
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getTypeResult() {
		return typeResult;
	}
	public void setTypeResult(Integer typeResult) {
		this.typeResult = typeResult;
	}
	public Student student;
	public Integer typeResult;
	
	public ResultImportStudent(Student student, Integer typeResult){
		this.student = student;
		this.typeResult = typeResult;
	}
}