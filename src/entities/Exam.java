package entities;

import java.math.BigInteger;

public class Exam extends AbstractExam {
	
	private BigInteger value;
	
	public Exam (String type, BigInteger value)
	{
		setType(type);
		setValue(value);
	}
		
	public BigInteger getValue() {
		return value;
	}
	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	public String toString() 
	{
		return ("Exam type: "+ getType() + ", value:" + getValue());
	}


}
