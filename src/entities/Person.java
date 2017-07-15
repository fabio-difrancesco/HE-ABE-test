package entities;

import java.util.List;

public class Person {
	
	private String firstName;
	private String lastName;
	private Gender gender;
	private List<AbstractExam> exams;

	public Person(String firstName, String lastName)
	{
		setFirstName(firstName);
		setLastName(lastName);
		
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public List<AbstractExam> getExams() {
		return exams;
	}
	public void setExams(List<AbstractExam> exams) {
		this.exams = exams;
	}
	public void addExam(AbstractExam exam)
	{
		this.exams.add(exam);
	}

}
