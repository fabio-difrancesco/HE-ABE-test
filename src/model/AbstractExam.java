package model;

public abstract class AbstractExam 
{
	
	private String type;
	protected Gender patientGender;
	protected int patientAge;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Gender getPatientGender() {
		return patientGender;
	}
	
	protected void setPatientGender(Gender patientGender) {
		this.patientGender = patientGender;
	}
	
	public int getPatientAge() {
		return patientAge;
	}
	
	protected void setPatientAge(int age) {
		this.patientAge = age;
	}


}
