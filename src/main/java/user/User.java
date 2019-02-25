/*
File: Example.java    
Date 	 	Author 	Changes
--------------------------------------------
Feb 11 19 	Tore 	Created
Feb 18 19 	Tore 	Added new functionality
Feb 19 19 	Tore 	Added new functionality
Feb 20 19 	David 	Added new bugfixes
*/

package user;

//import java.sql.Connection; 
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class User{
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Map<String, Character> myCourses; //Inneholder permission og fagkode, fagkode er n�kkelen. Se getPosition for kodeforklaring.
	
	
	public User(String username, String firstName, String lastName, String email, String password,
			Map<String, Character> coursesAndRoles) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.myCourses = coursesAndRoles;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	public Map<String, Character> getMyCourses() {
		return myCourses;
	}
	
	public char getPosition(String course) {
		return myCourses.get(course);
	}
}
