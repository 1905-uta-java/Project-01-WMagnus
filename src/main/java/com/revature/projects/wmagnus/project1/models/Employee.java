package com.revature.projects.wmagnus.project1.models;

import java.io.Serializable;

import com.revature.projects.wmagnus.project1.util.HashUtil;

public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_NAME_LENGTH = 32;
	public static final int MAX_EMAIL_LENGTH      = 32;
	public static final int MAX_USERNAME_LENGTH   = 32;
	//reference hashutil for hashlength
	
	private int employeeID;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String hashpass;
	private char needsReset;
	
	public Employee()
	{
		super();
	}
	
	public Employee(int employeeID, String firstName, String lastName, String email, String username, String hashpass, char needsReset)
	{
		if (email.length() <= MAX_EMAIL_LENGTH) this.email = email; else this.email = null;
		this.employeeID = employeeID;
		if(firstName.length() <= MAX_NAME_LENGTH) this.firstName = firstName; else this.firstName = null;
		if(lastName.length() <= MAX_NAME_LENGTH) this.lastName = lastName; else this.lastName = null;
		if(username.length() <= MAX_USERNAME_LENGTH) this.username = username; else this.username = null;
		if(hashpass.length() == HashUtil.HASH_PASS_EXACT_LEN) this.hashpass = hashpass; else this.hashpass = null;
		this.needsReset = needsReset;
	}
	
	public Employee(Employee e)
	{
		this.email = e.email;
		this.firstName = e.firstName;
		this.employeeID = e.employeeID;
		this.hashpass = e.hashpass;
		this.lastName = e.lastName;
		this.username = e.username;
	}
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if (firstName.length() <= MAX_NAME_LENGTH) this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if (lastName.length() <= MAX_NAME_LENGTH) this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if (email.length() <= MAX_EMAIL_LENGTH) this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if (username.length() <= MAX_USERNAME_LENGTH) this.username = username;
	}
	public String getHashpass() {
		return hashpass;
	}
	public void setHashpass(String hashpass) {
		//if (username.length() == HashUtil.HASH_PASS_EXACT_LEN) 
		this.hashpass = hashpass;
	}
	public char getNeedsReset() {
		return needsReset;
	}
	public void setNeedsReset(char needsReset) {
		this.needsReset = needsReset;
	}
	
	@Override
	public String toString()
	{
		return "employee={" + "employeeID:" + employeeID + ",first:" + firstName + ",last:" + lastName + ",email:" + email + ",username:" + username + ",hashpass:" + hashpass + ",needsreset:" + needsReset +"}";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		Employee e = (Employee) o;
		if(!e.email.equalsIgnoreCase(email)) return false;
		if(e.employeeID != employeeID) return false;
		if(!e.firstName.equalsIgnoreCase(firstName)) return false;
		if(!e.hashpass.equalsIgnoreCase(hashpass)) return false;
		if(!e.lastName.equalsIgnoreCase(lastName)) return false;
		if(e.needsReset != needsReset) return false;
		if(!e.username.equalsIgnoreCase(username)) return false;
		return true;
	}
	
}
