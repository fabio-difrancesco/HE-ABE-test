package entities;

import HomomorphicEncryption.EncryptedValue;

public class EncryptedExam extends AbstractExam{
	
	private EncryptedValue encryptedValue;
	
	public EncryptedExam (String type, EncryptedValue ev)
	{
		this.setType(type);
		this.setEncryptedValue(ev);
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

}
