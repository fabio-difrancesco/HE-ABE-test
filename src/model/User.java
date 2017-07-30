package model;

import java.util.List;

public class User {
	
	private String firstName;
	private String lastName;
	private Gender gender;
	private List<AbstractExam> exams;
	private int age;

	public User(String firstName, String lastName, Gender g, int age)
	{
		setFirstName(firstName);
		setLastName(lastName);
		setGender(g);
		setAge(age);
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
	
	public void setExams(List<AbstractExam> exams) 
	{
		for (AbstractExam exam : exams) {
			exam.setPatientGender(getGender());
		}
		this.exams = exams;
	}
	
	public void addExam(AbstractExam exam)
	{
		exam.setPatientGender(getGender());
		this.exams.add(exam);
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

}
