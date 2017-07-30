package model;

import java.math.BigInteger;

import HomomorphicEncryption.EncryptedValue;

public class EncryptedExam extends AbstractExam{
	
	private EncryptedValue encryptedValue;
	private User patient;
	
	public EncryptedExam (String type, EncryptedValue ev, User patient)
	{
		setType(type);
		setEncryptedValue(ev);
		setPatient(patient);
		setPatientGender(patient.getGender());
		setPatientAge(patient.getAge());
	}

	public EncryptedValue getEncryptedValue() {
		return encryptedValue;
	}

	public void setEncryptedValue(EncryptedValue encryptedValue) {
		this.encryptedValue = encryptedValue;
	}
	
	public String toString() 
	{
		return ("Exam type: "+ getType() + ", value is protected");
	}

	public EncryptedValue sum(EncryptedExam otherExam)
	{
		EncryptedValue result = this.encryptedValue.addValue(otherExam.getEncryptedValue());
		return result;
	}
	
	public EncryptedValue scale(BigInteger scalar)
	{
		EncryptedValue result = this.encryptedValue.scale(scalar);
		return result;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}
}
